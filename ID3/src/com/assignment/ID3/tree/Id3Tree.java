package com.assignment.ID3.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Id3Tree {

	private List<Record> records;
	private List<FieldType> types;
	private int targetAttribute;
	private Node root;

	public Id3Tree(List<Record> records, int targetAttribute,
			List<FieldType> types) {
		this.records = records;
		this.types = types;
		this.targetAttribute = targetAttribute;
	}

	public ColumnValues calculateDataAttributes(int field) {
		ColumnValues values = null;
		if (types.get(field) == FieldType.DISCRETE) {
			values = calculateStringAttributes(field);
		} else {
			values = calculateContinuousAttributes(field);
		}
		return values;
	}

	private ColumnValues calculateStringAttributes(int field) {
		ColumnValues values = new ColumnValues();
		ArrayList<Field<?>> diffValues = new ArrayList<Field<?>>();
		ArrayList<Integer> quantity = new ArrayList<Integer>();

		for (int i = 0; i < records.size(); i++) {
			int index = -1;

			for (int j = 0; j < diffValues.size(); j++) {
				if (((String) (records.get(i).getField(field).getValue()))
						.compareTo((String) diffValues.get(j).getValue()) == 0) {
					index = j;
					break;
				}
			}

			if (index == -1) {
				diffValues.add(records.get(i).getField(field));
				quantity.add(1);
			} else {
				quantity.set(index, quantity.get(index) + 1);
			}
		}
		values.setQuantity(quantity);
		values.setValues(diffValues);
		return values;
	}

	private ColumnValues calculateContinuousAttributes(final int field) {
		ColumnValues values = new ColumnValues();
		ArrayList<Field<?>> diffValues = new ArrayList<Field<?>>();
		ArrayList<Integer> quantity = new ArrayList<Integer>();

		Comparator<Record> numericComparator = new Comparator<Record>() {
			public int compare(Record rec1, Record rec2) {
				int result = 0;
				if ((Double) rec1.getField(field).getValue() < (Double) rec2
						.getField(field).getValue()) {
					result = -1;
				} else if ((Double) (rec1.getField(field).getValue()) == (Double) (rec2
						.getField(field).getValue())) {
					result = 0;
				} else if ((Double) rec1.getField(field).getValue() > (Double) rec2
						.getField(field).getValue()) {
					result = 1;
				}
				return result;
			}
		};

		Collections.sort(records, numericComparator);

		String outcome = (String) records.get(0).getField(targetAttribute)
				.getValue();

		for (int i = 1; i < records.size(); i++) {
			if (outcome.compareTo((String) records.get(i)
					.getField(targetAttribute).getValue()) != 0) {
				diffValues.add(records.get(i).getField(field));
				quantity.add(1);
				outcome = (String) records.get(i).getField(targetAttribute)
						.getValue();
			} else {
				quantity.set(quantity.size() - 1,
						quantity.get(quantity.size() - 1) + 1);
			}
		}

		values.setQuantity(quantity);
		values.setValues(diffValues);
		return values;
	}

	private ArrayList<ArrayList<Record>> splitDiscrete(
			List<Record> records, final int field) {
		ArrayList<ArrayList<Record>> result = new ArrayList<ArrayList<Record>>();

		Comparator<Record> discreteComparator = new Comparator<Record>() {
			public int compare(Record rec1, Record rec2) {
				return ((String) (rec1.getField(field).getValue()))
						.compareTo((String) rec2.getField(field).getValue());
			}
		};

		Collections.sort(records, discreteComparator);

		String prev = "";
		for (Record r : records) {
			if (((String) (r.getField(field).getValue())).compareTo(prev) != 0) {
				result.add(new ArrayList<Record>());
				prev = (String) (r.getField(field).getValue());
			}
			result.get(result.size() - 1).add(r);
		}
		return result;
	}

	private ArrayList<ArrayList<Record>> splitContinuous(List<Record> records, final int field) {
		ArrayList<ArrayList<Record>> result = new ArrayList<ArrayList<Record>>();

		Comparator<Record> numericComparator = new Comparator<Record>() {
			public int compare(Record rec1, Record rec2) {
				int result = 0;
				if ((Double) rec1.getField(field).getValue() < (Double) rec2.getField(field).getValue()) {
					result = -1;
				} else if ((Double) (rec1.getField(field).getValue()) == (Double) (rec2.getField(field).getValue())) {
					result = 0;
				} else if ((Double) rec1.getField(field).getValue() > (Double) rec2.getField(field).getValue()) {
					result = 1;
				}
				return result;
			}
		};

		Collections.sort(records, numericComparator);

		String prevOutcome = "";
		for (int i = 0; i < records.size(); i++) {
			if (prevOutcome.compareTo((String) records.get(i).getField(targetAttribute).getValue()) != 0) {
				result.add(new ArrayList<Record>());
				prevOutcome = (String) records.get(i).getField(targetAttribute).getValue();
			} 
			result.get(result.size()-1).add(records.get(i));
		}
		
		return result;
	}

	private Node buildTree(List<Record> records, List<Integer> remainingCols) {
		if(remainingCols.size() == 1){
			return new Node(remainingCols.get(0));
		}else if(sameTarget(records)){
			return new Node(-1);
		}
		else{
			double maxInfoGain = 0;
			int maxInfoGainOffset = -1;
			for(Integer index : remainingCols){
				ArrayList<ArrayList<Record>> splitted;
				if(types.get(index) == FieldType.DISCRETE){
					splitted = splitDiscrete(records, index);
				}else{
					splitted = splitContinuous(records, index);
				}
				double gain;
				if((gain = new Heuristic().calculateInformationGain(convertForGain(splitted, index))) > maxInfoGain){
					maxInfoGain = gain;
					maxInfoGainOffset = index;
				}
			}
			
			ArrayList<ArrayList<Record>> splitted;
			if(types.get(maxInfoGainOffset) == FieldType.DISCRETE){
				splitted = splitDiscrete(records, maxInfoGainOffset);
			}else{
				splitted = splitContinuous(records, maxInfoGainOffset);
			}
			
			remainingCols.remove(remainingCols.indexOf(maxInfoGainOffset));
			
			Node parent = new Node(maxInfoGainOffset);
			parent.setRecords(splitted);
			
			for(ArrayList<Record> list : splitted){
				parent.addChild(buildTree(list, new ArrayList<Integer>(remainingCols)));
			}
			
			return parent;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<ArrayList<InfoGainPair>> convertForGain(ArrayList<ArrayList<Record>> splitted, int targetField) {
		ArrayList<ArrayList<InfoGainPair>> result = new ArrayList<ArrayList<InfoGainPair>>();
		for(ArrayList<Record> list : splitted){
			ArrayList<InfoGainPair> add = new ArrayList<InfoGainPair>();
			for(Record r : list){
				add.add(new InfoGainPair(r.getField(targetField), (Field<String>)r.getField(targetAttribute)));
			}
			result.add(add);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private boolean sameTarget(List<Record> records){
		ArrayList<Field<String>> outcomes = new ArrayList<Field<String>>();
		for(Record r: records){
			outcomes.add((Field<String>)r.getField(targetAttribute));
		}
		return new AttributeCounter().calculateStringAttributes(outcomes).getValues().size() == 1;
	}

	public void generateTree() {
		ArrayList<Integer> remainingCols = new ArrayList<Integer>();
		for(int i = 0; i < types.size(); i++){
			if(i != targetAttribute){
				remainingCols.add(i);
			}
		}
		root = buildTree(records, remainingCols);
	}
	
	public void printTree(){
		toString(root, -1);
	}
	
	public void toString(Node n, int prevOffset){
		for(int i = 0; i < n.getChildren().size(); i++){
			if(prevOffset != -1){
				System.out.println(n.getRecords().get(i).get(0).getField(prevOffset).getValue() + "->" + n.getName() + "[label=\"" + "" +"\"");
			}
			if(n.getRecords() != null){
				System.out.println(n.getName() + "->" + n.getRecords().get(i).get(0).getField(n.getOffset()).getValue());
			}else{
				//System.out.println(n.getName() )
			}
			toString(n.getChildren().get(i), n.getOffset());
		}
	}
}

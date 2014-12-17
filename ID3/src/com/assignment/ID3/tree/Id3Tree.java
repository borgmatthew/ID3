package com.assignment.ID3.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Id3Tree {

	private List<Record> records;
	private List<FieldType> types;
	private int targetAttribute;

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
				if ((Double) rec1.getField(field).getValue() < (Double) rec2.getField(field).getValue()) {
					result = -1;
				} else if ((Double) (rec1.getField(field).getValue()) == (Double) (rec2.getField(field).getValue())) {
					result = 0;
				} else if ((Double) rec1.getField(field).getValue() > (Double) rec2.getField(field).getValue()){
					result = 1;
				}
				return result;
			}
		};

		Collections.sort(records, numericComparator);

		String outcome = (String) records.get(0).getField(targetAttribute).getValue();

		for (int i = 1; i < records.size(); i++) {
			if (outcome.compareTo((String) records.get(i)
					.getField(targetAttribute).getValue()) != 0) {
				diffValues.add(records.get(i).getField(field));
				quantity.add(1);
				outcome = (String) records.get(i).getField(targetAttribute).getValue();
			} else {
				quantity.set(quantity.size() - 1,
						quantity.get(quantity.size() - 1) + 1);
			}
		}

		values.setQuantity(quantity);
		values.setValues(diffValues);
		return values;
	}
	
	private void splitDiscrete(ArrayList<Record> records){
		
	}
	
	private void splitContinuous(){
		
	}
	
	public void buildTree(){
		
	}
}

package com.assignment.ID3.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Id3Tree {

	private List<FieldType> types;
	private List<Record> trainRecords;
	private List<Record> verificationRecords;
	private List<Boolean> useFields;
	private HeuristicType heuristic;
	private int targetAttribute;
	private Node root;
	private int nodeId = 0;

	public Id3Tree(List<Record> records, int targetAttribute,
			List<FieldType> types, List<Boolean> toUse, double trainRatio,
			HeuristicType heuristicType) {
		this.types = types;
		this.heuristic = heuristicType;
		this.targetAttribute = targetAttribute;
		this.useFields = toUse;
		if (trainRatio == 1) {
			trainRecords = records;
		} else {
			trainRecords = new ArrayList<Record>();
			Random r = new Random();
			int size = records.size();
			for (int i = 0; i < size * trainRatio; i++) {
				int index = r.nextInt(records.size());
				trainRecords.add(records.get(index));
				records.remove(index);
			}
			verificationRecords = records;
		}
	}

	private ArrayList<ArrayList<Record>> splitDiscrete(List<Record> records,
			final int field) {
		ArrayList<ArrayList<Record>> result = new ArrayList<ArrayList<Record>>();

		Comparator<Record> discreteComparator = new Comparator<Record>() {
			public int compare(Record rec1, Record rec2) {
				return ((String) (rec1.getField(field).getValue()))
						.compareTo((String) rec2.getField(field).getValue());
			}
		};

		Collections.sort(records, discreteComparator);

		String prevValue = "";
		for (Record r : records) {
			if (((String) (r.getField(field).getValue())).compareTo(prevValue) != 0) {
				result.add(new ArrayList<Record>());
				prevValue = (String) (r.getField(field).getValue());
			}
			result.get(result.size() - 1).add(r);
		}
		return result;
	}

	private ArrayList<ArrayList<Record>> splitContinuous(List<Record> records,
			final int field) {
		ArrayList<ArrayList<Record>> result = new ArrayList<ArrayList<Record>>();

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

		String prevOutcome = (String) records.get(0).getField(targetAttribute)
				.getValue();
		double prevValue = (Double) records.get(0).getField(field).getValue();
		result.add(new ArrayList<Record>());
		result.get(0).add(records.get(0));

		for (int i = 1; i < records.size(); i++) {
			if (prevOutcome.compareTo((String) records.get(i)
					.getField(targetAttribute).getValue()) != 0
					&& prevValue != (Double) records.get(i).getField(field)
							.getValue()) {
				result.add(new ArrayList<Record>());
			}
			prevValue = (Double) records.get(i).getField(field).getValue();
			prevOutcome = (String) records.get(i).getField(targetAttribute)
					.getValue();
			result.get(result.size() - 1).add(records.get(i));
		}

		return result;
	}

	private Node buildTree(List<Record> records, List<Integer> remainingCols) {
		if (remainingCols.size() == 1) {
			Node n = new Node(remainingCols.get(0), nodeId++);
			ArrayList<ArrayList<Record>> tmpList = new ArrayList<ArrayList<Record>>();
			tmpList.add(new ArrayList<Record>(records));
			n.setRecords(tmpList);
			return n;
		} else if (sameTarget(records)) {
			Node n = new Node(targetAttribute, nodeId++);
			ArrayList<ArrayList<Record>> tmpList = new ArrayList<ArrayList<Record>>();
			tmpList.add(new ArrayList<Record>(records));
			n.setRecords(tmpList);
			return n;
		} else {
			double maxInfoGain = -1;
			int maxInfoGainOffset = -1;
			for (Integer index : remainingCols) {
				ArrayList<ArrayList<Record>> splitted;
				if (types.get(index) == FieldType.DISCRETE) {
					splitted = splitDiscrete(records, index);
				} else {
					splitted = splitContinuous(records, index);
				}
				double gain;
				if (heuristic == HeuristicType.InfoGain) {
					gain = new Heuristic()
							.calculateInformationGain(convertForGain(splitted,
									index));
				} else {
					gain = new Heuristic().gainRatio(convertForGain(splitted,
							index));
				}

				if (gain >= maxInfoGain) {
					maxInfoGain = gain;
					maxInfoGainOffset = index;
				}

			}
			System.out.println("offset: " + maxInfoGainOffset + " ratio: " + maxInfoGain);
			ArrayList<ArrayList<Record>> splitted;
			if (types.get(maxInfoGainOffset) == FieldType.DISCRETE) {
				splitted = splitDiscrete(records, maxInfoGainOffset);
			} else {
				splitted = splitContinuous(records, maxInfoGainOffset);
			}

			remainingCols.remove(remainingCols.indexOf(maxInfoGainOffset));

			Node parent = new Node(maxInfoGainOffset, nodeId++);
			parent.setRecords(splitted);

			for (ArrayList<Record> list : splitted) {
				parent.addChild(buildTree(list, new ArrayList<Integer>(
						remainingCols)));
			}

			return parent;
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<ArrayList<InfoGainPair>> convertForGain(
			ArrayList<ArrayList<Record>> splitted, int targetField) {
		ArrayList<ArrayList<InfoGainPair>> result = new ArrayList<ArrayList<InfoGainPair>>();
		for (ArrayList<Record> list : splitted) {
			ArrayList<InfoGainPair> add = new ArrayList<InfoGainPair>();
			for (Record r : list) {
				add.add(new InfoGainPair(r.getField(targetField),
						(Field<String>) r.getField(targetAttribute)));
			}
			result.add(add);
		}
		return result;
	}

	private boolean sameTarget(List<Record> records) {
		String prev = (String)records.get(0).getField(targetAttribute).getValue();
		for (Record r : records) {
			if(prev.compareTo((String)r.getField(targetAttribute).getValue()) != 0){
				return false;
			}
		}
		return true;
	}

	public void generateTree() {
		ArrayList<Integer> remainingCols = new ArrayList<Integer>();
		for (int i = 0; i < types.size(); i++) {
			if (i != targetAttribute && useFields.get(i)) {
				remainingCols.add(i);
			}
		}
		root = buildTree(trainRecords, remainingCols);
	}

	public String printTree() {
		StringBuilder builder = new StringBuilder("digraph tree {\n");
		toString(root, -1, ++nodeId, builder);
		builder.append("}");
		return builder.toString();
	}

	public int toString(Node parent, int prevOffset, int counter,
			StringBuilder builder) {

		if (parent.getChildren().size() > 0) {
			builder.append(parent.getNodeId() + " [label = \""
					+ parent.getName() + "\"]\n");
		}
		for (int i = 0; i < parent.getChildren().size(); i++) {

			if (parent.getChildren().get(i).getChildren().size() != 0) {
				builder.append(parent.getChildren().get(i).getNodeId()	+ " [label = \"" + parent.getChildren().get(i).getName() + "\"]\n");
				builder.append(parent.getNodeId() + "->" + parent.getChildren().get(i).getNodeId() + "[label = \"" + parent.getRecords().get(i).get(0).getField(parent.getOffset()).getValue() + "\"]\n");
			} else {
				builder.append(parent.getChildren().get(i).getNodeId() + " [shape = \"box\" label = \""	+ (String) parent.getRecords().get(i).get(0).getField(targetAttribute).getValue() + "\"]\n");
				builder.append(parent.getNodeId() + "->"  + parent.getChildren().get(i).getNodeId() + "[label = \""	+ parent.getRecords().get(i).get(0).getField(parent.getOffset()).getValue()	+ "\"]\n");
			}
			counter = toString(parent.getChildren().get(i), parent.getOffset(),
					counter, builder);
		}
		return counter;
	}

	private String classify(Node n, Record r) {
		if (n.getChildren().size() != 0) {
			int fieldId = n.getOffset();
			int childOffset = -1;
			for (int i = 0; i < n.getChildren().size(); i++) {
				if (types.get(fieldId) == FieldType.DISCRETE) {
					if (((String) (r.getField(fieldId).getValue()))
							.compareTo((String) n.getRecords().get(i).get(0)
									.getField(fieldId).getValue()) == 0) {
						childOffset = i;
						break;
					}
				} else {
					double value = (Double) r.getField(fieldId).getValue();
					int maxSize = n.getRecords().get(i).size();
					if (value >= (Double) n.getRecords().get(i).get(0)
							.getField(fieldId).getValue()
							&& value <= (Double) n.getRecords().get(i)
									.get(maxSize - 1).getField(fieldId)
									.getValue()) {
						childOffset = i;
						break;
					}
				}
			}
			if (childOffset != -1) {
				return classify(n.getChildren().get(childOffset), r);
			} else {
				return "UNCLASSIFIED";
			}
		} else {
			return (String) n.getRecords().get(0).get(0)
					.getField(targetAttribute).getValue();
		}
	}

	public int verifyTree() {
		int good = 0;
		for (Record r : verificationRecords) {
			String outcome = classify(root, r);
			if (outcome.compareTo((String) r.getField(targetAttribute)
					.getValue()) == 0) {
				good++;
			}
		}
		return good;
	}

	@SuppressWarnings("unchecked")
	private String countMostFrequentOutcome(Node n) {
		ArrayList<ArrayList<Record>> records = n.getRecords();
		ArrayList<Field<String>> outcomesOnly = new ArrayList<Field<String>>();
		for (ArrayList<Record> list : records) {
			for (Record r : list) {
				outcomesOnly.add((Field<String>) r.getField(targetAttribute));
			}
		}
		ColumnValues cv = new AttributeCounter()
				.calculateStringAttributes(outcomesOnly);
		int maxIndex = 0;
		int max = 0;
		for (int i = 1; i < cv.getQuantities().size(); i++) {
			if (cv.getQuantities().get(i) > max) {
				maxIndex = i;
			}
		}
		return (String) cv.getValues().get(maxIndex).getValue();
	}

	public void removeOverfitting() {
		if (verificationRecords != null) {
			pruneTree(root);
		}
	}

	public int pruneTree(Node n) {
		if (n.getChildren().size() == 0) {
			return verifyTree();
		} else {
			int goodClassifications = 0;
			for (int i = 0; i < n.getChildren().size(); i++) {
				goodClassifications = pruneTree(n.getChildren().get(i));
				Node child = n.getChildren().get(i);
				String outcome = countMostFrequentOutcome(n.getChildren()
						.get(i));
				Node temp = new Node(child.getOffset(), child.getNodeId());
				createTempNode(temp, outcome);

				n.getChildren().set(i, temp);
				int newGoodClassifications = verifyTree();
				if (newGoodClassifications < goodClassifications) {
					n.getChildren().set(i, child);
				}
			}
			return goodClassifications;
		}
	}

	private void createTempNode(Node temp, String outcome) {
		ArrayList<ArrayList<Record>> tempList = new ArrayList<ArrayList<Record>>();
		ArrayList<Record> tempList1 = new ArrayList<Record>();
		Record r = new Record();
		for (int j = 0; j < targetAttribute; j++) {
			r.add(new Field<String>(""));
		}
		r.add(new Field<String>(outcome));
		tempList1.add(r);
		tempList.add(tempList1);
		temp.setRecords(tempList);
	}
}

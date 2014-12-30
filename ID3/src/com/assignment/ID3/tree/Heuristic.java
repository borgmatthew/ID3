package com.assignment.ID3.tree;

import java.util.ArrayList;

public class Heuristic {

	public Heuristic(){
	}
	
	public double calculateEntropy(ArrayList<Field<String>> outcomes){
		ColumnValues cv = new AttributeCounter().calculateStringAttributes(outcomes);
		double totalEntropy = 0;
		
		int totalRecords = 0;
		for(int i = 0; i < cv.getValues().size(); i++){
			totalRecords += cv.getQuantities().get(i);
		}
		
		for(Integer i: cv.getQuantities()){
			totalEntropy += -((double)i/totalRecords) * (Math.log((double)i/totalRecords) / Math.log(2.0d));
		}
		
		return totalEntropy;
	}
	
	public double calculateInformationGain(ArrayList<ArrayList<InfoGainPair>> splittedPairs){
		ArrayList<Field<String>> outcomes = new ArrayList<Field<String>>();
		int totalElements = 0;
		for(ArrayList<InfoGainPair> list : splittedPairs){
			for(InfoGainPair p : list){
				outcomes.add(p.getOutcome());
				totalElements++;
			}
		}
		
		double totalEntropy = calculateEntropy(outcomes);

		double sumEntropies = 0;
		for(ArrayList<InfoGainPair> list : splittedPairs){
			outcomes = new ArrayList<Field<String>>();
			for(InfoGainPair p : list){
				outcomes.add(p.getOutcome());
			}
			sumEntropies += (list.size() / totalElements) * calculateEntropy(outcomes);
		}
		
		return totalEntropy - sumEntropies;
	}
}

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
			sumEntropies += ((double)list.size() / (double)totalElements) * calculateEntropy(outcomes);
		}
		
		return totalEntropy - sumEntropies;
	}
	
	private double splitInformation(ArrayList<ArrayList<InfoGainPair>> splittedPairs){
		int totalElements = 0;
		for(ArrayList<InfoGainPair> list : splittedPairs){
			totalElements += list.size();
		}
		
		double sumSplitInfo = 0;
		for(ArrayList<InfoGainPair> list : splittedPairs){
			double ratio = ((double)list.size() / (double)totalElements);
			sumSplitInfo +=  ratio * Math.log(ratio)/Math.log(2.0d);
		}
		return -sumSplitInfo;
	}
	
	public double gainRatio(ArrayList<ArrayList<InfoGainPair>> splittedPairs){
		double split = splitInformation(splittedPairs);
		if(split != 0){
			return calculateInformationGain(splittedPairs)/split;
		}else{
			return 0;
		}
	}
}

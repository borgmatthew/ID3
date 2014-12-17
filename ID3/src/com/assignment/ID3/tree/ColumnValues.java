package com.assignment.ID3.tree;

import java.util.ArrayList;

public class ColumnValues {

	private ArrayList<Field<?>> values;
	private ArrayList<Integer> quantity;
	
	protected ColumnValues(){
	}
	
	public void setValues(ArrayList<Field<?>> values){
		this.values = values; 
	}
	
	public void setQuantity(ArrayList<Integer> quantity){
		this.quantity = quantity;
	}
	
	public ArrayList<Field<?>> getValues(){
		return values;
	}
	
	public ArrayList<Integer> getQuantities(){
		return quantity;
	}
}

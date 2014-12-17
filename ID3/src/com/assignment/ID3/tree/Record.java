package com.assignment.ID3.tree;

import java.util.ArrayList;

public class Record {

	private ArrayList<Field<?>> values = new ArrayList<Field<?>>();
	
	public Record(){
		
	}
	
	public ArrayList<Field<?>> getFields(){
		return values;
	}
	
	public void add(Field<?> toAdd){
		values.add(toAdd);
	}
	
	public Field<?> getField(int offset){
		return values.get(offset);
	}
}

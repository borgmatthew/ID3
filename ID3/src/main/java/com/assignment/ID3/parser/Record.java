package com.assignment.ID3.parser;

import java.util.ArrayList;

public class Record {

	private ArrayList<Field<?>> values;
	
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

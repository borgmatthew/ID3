package com.assignment.ID3.parser;

import java.util.ArrayList;

import com.assignment.ID3.tree.Field;
import com.assignment.ID3.tree.FieldType;
import com.assignment.ID3.tree.Record;

public class HeaderInfo {

	private Record record;
	private ArrayList<FieldType> types = new ArrayList<FieldType>();
	
	public HeaderInfo(Record record){
		this.record = record;
		checkTypes();
	}
	
	private void checkTypes(){
		for(Field<?> f : record.getFields()){
			if(isNumeric((String)f.getValue())){
				types.add(FieldType.CONTINUOUS);
			}else{
				types.add(FieldType.DISCRETE);
			}
		}
	}
	
	private boolean isNumeric(String check){
		boolean result = false;
		try{
			Double.parseDouble(check);
			result = true;
		}catch(NumberFormatException nfe){
			result = false;
		}
		return result;
	}
	
	public ArrayList<FieldType> getTypes(){
		return types;
	}
}

package com.assignment.ID3.parser;

import java.util.ArrayList;

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
				types.add(FieldType.NUMERIC);
			}else{
				types.add(FieldType.STRING);
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

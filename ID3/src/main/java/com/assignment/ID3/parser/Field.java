package com.assignment.ID3.parser;

public class Field<T> {

	private T value;
	
	public Field(T value){
		this.value = value;
	}
	
	public T getValue(){
		return value;
	}
}

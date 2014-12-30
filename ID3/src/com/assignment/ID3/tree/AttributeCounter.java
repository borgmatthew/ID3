package com.assignment.ID3.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AttributeCounter {
	
	public ColumnValues calculateStringAttributes(ArrayList<Field<String>> coloumn) {
		ColumnValues values = new ColumnValues();
		ArrayList<Field<?>> diffValues = new ArrayList<Field<?>>();
		ArrayList<Integer> quantity = new ArrayList<Integer>();

		Comparator<Field<String>> discreteComparator = new Comparator<Field<String>>() {
			public int compare(Field<String> f1, Field<String> f2) {
				return f1.getValue().compareTo(f2.getValue());
			}
		};
		
		Collections.sort(coloumn, discreteComparator);
		
		String prev = "";
		for(Field<String> f : coloumn){
			if(f.getValue().compareTo(prev) != 0){
				diffValues.add((Field<?>)f);
				quantity.add(1);
				prev = f.getValue();
			}else{
				quantity.set(quantity.size()-1, quantity.get(quantity.size()-1) + 1);
			}			
		}
		
		values.setQuantity(quantity);
		values.setValues(diffValues);
		return values;
	}
}

package com.assignment.ID3.parser;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	private ArrayList<FieldType> fieldTypes;
	private ArrayList<Record> records = new ArrayList<Record>();
	private final String splitter = ",";

	public Parser() {
	}
	
	public ArrayList<FieldType> getFieldTypes(){
		return fieldTypes;
	}
	
	public ArrayList<Record> getRecords(){
		return records;
	}

	public void parse(String fileName) {
		FileHandler file = new FileHandler(fileName);
		if (file.openFile()) {
			try {
				String line = file.getNextLine();
				String[] fields = line.split(splitter);
				fieldTypes = new HeaderInfo(getFirstRecord(fields)).getTypes();

				do {
					fields = line.split(splitter);
					records.add(createRecord(fields));
				} while ((line = file.getNextLine()) != null);
			} catch (IOException ioe) {
				System.out.println("There was an IO error when parsing the file");
			} finally {
				file.closeFile();
			}
		}else{
			System.out.println("File was not opened");
		}
	}

	private Record createRecord(String[] fields) {
		Record record = new Record();
		for (int i = 0; i < fields.length; i++) {
			if (fieldTypes.get(i) == FieldType.NUMERIC) {
				double value = Double.parseDouble(fields[i]);
				record.add(new Field<Double>(value));
			} else {
				record.add(new Field<String>(fields[i]));
			}
		}
		return record;
	}

	private Record getFirstRecord(String[] fields) {
		Record firstRecord = new Record();
		for (int i = 0; i < fields.length; i++) {
			firstRecord.add(new Field<String>(fields[i]));
		}
		return firstRecord;
	}
}

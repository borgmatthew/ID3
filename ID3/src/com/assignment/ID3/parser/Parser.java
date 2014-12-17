package com.assignment.ID3.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.assignment.ID3.tree.Field;
import com.assignment.ID3.tree.FieldType;
import com.assignment.ID3.tree.Record;

public class Parser {

	private ArrayList<FieldType> fieldTypes = null;
	private ArrayList<Record> records = new ArrayList<Record>();
	private final String splitter = ",";
	private FileHandler fileHandler;
	private String message;

	public Parser(String fileName, ArrayList<FieldType> types) {
		fileHandler = new FileHandler(fileName);
		this.fieldTypes = types;
	}

	public String getMessage(){
		return message;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public boolean parse() {
		if (fileHandler.openFile()) {
			try {		
				String line;
				String[] fields;

				while ((line = fileHandler.getNextLine()) != null){
					fields = line.split(splitter);
					records.add(createRecord(fields));
				}
			} catch (IOException ioe) {
				message = "An error occured while reading from file";
				return false;
			} finally {
				fileHandler.closeFile();
			}
			message = "File parsed successfully";
			return true;
		} else {
			message = "An error occured while opening the file";
			return false;
		}
	}

	private Record createRecord(String[] fields) {
		Record record = new Record();
		for (int i = 0; i < fields.length; i++) {
			if (fieldTypes.get(i) == FieldType.CONTINUOUS) {
				double value = Double.parseDouble(fields[i]);
				record.add(new Field<Double>(value));
			} else {
				record.add(new Field<String>(fields[i]));
			}
		}
		return record;
	}
}

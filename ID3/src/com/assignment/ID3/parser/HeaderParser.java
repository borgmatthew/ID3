package com.assignment.ID3.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.assignment.ID3.tree.Field;
import com.assignment.ID3.tree.FieldType;
import com.assignment.ID3.tree.Record;

public class HeaderParser {

	private FileHandler fileHandler;
	private final String splitter = "\\s+";

	public HeaderParser(String fileName) {
		this.fileHandler = new FileHandler(fileName);
	}
	
	public ArrayList<FieldType> parseHeaders() {
		ArrayList<FieldType> fieldTypes = new ArrayList<FieldType>();
		if (fileHandler.openFile()) {
			try {
				String line = fileHandler.getNextLine();
				line = line.replaceAll(splitter, ",");
				String[] fields = line.split(",");
				fieldTypes = new HeaderInfo(getFirstRecord(fields)).getTypes();
			} catch (IOException ioe) {
				fieldTypes = null;
			} finally {
				fileHandler.closeFile();
			}
		}
		return fieldTypes;
	}
	
	private Record getFirstRecord(String[] fields) {
		Record firstRecord = new Record();
		for (int i = 0; i < fields.length; i++) {
			firstRecord.add(new Field<String>(fields[i]));
		}
		return firstRecord;
	}
}

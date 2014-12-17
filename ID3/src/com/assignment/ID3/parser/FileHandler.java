package com.assignment.ID3.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
	
	private File file;
	private FileReader fileReader = null;
	private BufferedReader bufferedReader;
	
	public FileHandler(String fileName){
		file = new File(fileName);
	}

	public boolean openFile(){
		try{
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	public boolean closeFile(){
		try{
			if(bufferedReader != null){
				bufferedReader.close();
			}
			
			if(fileReader != null){
				fileReader.close();
			}
			
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	public String getNextLine() throws IOException{
		return bufferedReader.readLine();
	}
}

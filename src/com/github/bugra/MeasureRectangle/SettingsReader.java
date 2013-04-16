package com.github.bugra.MeasureRectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsReader {
	private static volatile SettingsReader	instance = null;
	private static final String FILE_NAME = "settings.txt"; 
	private static final String SETTINGS = "Asynchronous: 1\nGrid: 1";
	private static final String DELIMETER = ":";
	private static HashMap<String, Integer> settings;
	
	private SettingsReader(){
		
	}
	
	public static SettingsReader getInstance(){
		if (instance == null){
			synchronized(SettingsReader.class){
				if(instance == null){
					instance = new SettingsReader();
				}
			}
		}
		return instance;
	}
	
	public HashMap<String, Integer> readFile() throws IOException{
		settings = new HashMap<String, Integer>();
		BufferedReader br = null;
		boolean validate = false;
		try {
			String sCurrentLine;
			File file = new File(FILE_NAME);
			if(!file.exists()) {
			    file.createNewFile();
			    FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(SETTINGS);
				bw.close();
			}
			br = new BufferedReader(new FileReader(FILE_NAME));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] tokens = sCurrentLine.split(DELIMETER);
				int lastElement = tokens.length;
				settings.put(tokens[0].trim(), Integer.parseInt(tokens[lastElement-1].trim()));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if(settings.containsKey("Grid") & settings.containsKey("Asynchronous")){
			if((settings.get("Grid") == 0 | settings.get("Grid") == 1 ) & 
			   (settings.get("Asynchronous") == 1 | settings.get("Asynchronous") == 1)){
				validate = true;
			}else{
				createFile();
				readFile();
			}
		}else{
			createFile();
			readFile();
		}
		if (!validate){
			createFile();
			readFile();
		}
		return settings;
	}
	
	public void createFile() throws IOException{
		File file = new File(FILE_NAME);
		if(!file.exists()) {
		    file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SETTINGS);
		bw.close();

		System.out.println("Done");
	}
	
	public static void main(String[] args) throws IOException{
		SettingsReader sr = SettingsReader.getInstance();
		HashMap<String, Integer> settings = new HashMap<String, Integer>();
		settings = sr.readFile();
		System.out.println(settings.toString());
	}
	
}

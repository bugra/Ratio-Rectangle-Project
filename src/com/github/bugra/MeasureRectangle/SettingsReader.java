package com.github.bugra.MeasureRectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsReader {
	private static volatile SettingsReader	instance = null;
	private static final String FILE_NAME = "settings.txt"; 
	private static final int SETTINGS_NUMBER = 1;
	private static final String SETTINGS = "Asynchronous: 1";
	private static final String DELIMETER = ":";
	private static int[] settings;
	
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
	
	public int[] readFile() throws IOException{
		settings = new int[SETTINGS_NUMBER];
		createFile();
		BufferedReader br = null;
		try {
 
			String sCurrentLine;
			int temp = 0;
			br = new BufferedReader(new FileReader(FILE_NAME));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] tokens = sCurrentLine.split(DELIMETER);
				int lastElement = tokens.length;
				settings[temp] = Integer.parseInt(tokens[lastElement-1].trim());
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
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
		int[] settings = new int[1];
		settings = sr.readFile();
		System.out.println(settings[0]);
	}
	
}

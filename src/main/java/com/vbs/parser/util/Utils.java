package com.vbs.parser.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vbs.parser.domain.Statement;
import com.vbs.parser.workhorse.StatementFilter;

public class Utils {

	final static Logger logger = Logger.getLogger(Utils.class);
	
	public static List<Statement> filterStatements(List<Statement> list, StatementFilter filter){
		
		List<Statement> result = new ArrayList<Statement>();
		
		for (Statement item : list) {
			if(item.getType().equals(filter.getFilterClass())){
				result.add(item);
			}
		}
		
		return result;
	}
	
	public static List<String> getLines(File inputfile){
		List<String> lines = new ArrayList<String>();
		
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(inputfile));

			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine);
			}

		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				logger.error(ex);
			}
		}
		
		return lines;
	}
	
	public static void writeLinesToFile(List<String> lines, File outputfile){
		
		BufferedWriter bw = null;
		try {
			StringBuilder builder = new StringBuilder();
			// if file doesnt exists, then create it
			if (!outputfile.exists()) {
				outputfile.createNewFile();
			}
			
			for(String line : lines) {
				builder.append(line + "\n");
			}

			FileWriter fw = new FileWriter(outputfile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(builder.toString());
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			logger.error(e);
		} finally{
			try {
				if (bw != null)bw.close();
			} catch (IOException ex) {
				logger.error(ex);
			}
		}
		
	}
	
	
}

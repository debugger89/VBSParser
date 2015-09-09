package com.vbs.parser.workhorse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vbs.parser.domain.Constants;
import com.vbs.parser.domain.DataType;
import com.vbs.parser.domain.ElseIfStatement;
import com.vbs.parser.domain.ElseStatement;
import com.vbs.parser.domain.Function;
import com.vbs.parser.domain.IfStatement;
import com.vbs.parser.domain.VBStatement;
import com.vbs.parser.domain.LoopStatement;
import com.vbs.parser.domain.Parameter;
import com.vbs.parser.domain.VariableInit;


public class StatementFactory {
		
		
	public static Function buildFunctionStatements(int index, String line) {
		
		Pattern pattern = Pattern.compile("( +.*?)\\(", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		Function func = new Function(matcher.group(1));	
		func.setLineNumber(index);
		func.setText(line);
		
		pattern = Pattern.compile("\\((.*?)\\)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(line);
		matcher.find();
		String paramstr = null;
		paramstr = matcher.group(1);
				
		if(paramstr != null){
			
			String[] params = paramstr.split(",");
			for(String param : params){
				param = param.trim();
				DataType paramType = DataType.VAR;
				String paramName = param;
				String [] pvPair = param.split("( +)");
				if(pvPair.length > 1){
					paramType = DataType.valueOf(pvPair[0].trim());
					paramName = pvPair[1].trim();
				}			
							
				Parameter p = new Parameter(paramName, paramType);
				func.getParameters().add(p);
			}
		}
		
		return func;
	}
	
	public static VariableInit buildVariableInitStatements(int index, String lineTrimmed) {
		
		Pattern pattern = Pattern.compile("Dim(.+?)As", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(lineTrimmed);
		matcher.find();
		
		VariableInit vinit = new VariableInit();
		vinit.setText(lineTrimmed);
		vinit.setLineNumber(index);
		
		String varstr = matcher.group(1);
		String[] variableNames = varstr.split(",");
		
		for(String var : variableNames){
			vinit.getVariables().add(var.trim());
		}
		
		pattern = Pattern.compile("As(.+)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(lineTrimmed);
		matcher.find();
		String vartype = matcher.group(1);
		vinit.setVariableType(DataType.fromString(vartype.trim()));
		
		return vinit;
		
	}
	
	public static VBStatement buildGenericStatements(int index, String line) {
		
		VBStatement stmt = new VBStatement();
		stmt.setLineNumber(index);
		stmt.setText(line);
		
		return stmt;
		
	}

	public static LoopStatement buildLoopStatements(int index,
			String lineTrimmed) {
		
		LoopStatement loop = new LoopStatement();
		
		loop.setText(lineTrimmed);
		loop.setLineNumber(index);
		
		String loopStartRegex = Constants.WHILE_IDENTIFIER+"|"+Constants.DO_WHILE_IDENTIFIER+"|"+Constants.FOR_IDENTIFIER;		
		String condition = lineTrimmed.replaceFirst(loopStartRegex, "");
		
		loop.setCondition(condition);
		
		return loop;
	}

	public static IfStatement buildIFStatements(int index, String lineTrimmed) {
		
		Pattern pattern = Pattern.compile("if(.+)then", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(lineTrimmed);
		matcher.find();
		
		IfStatement ifstmt = new IfStatement();
		ifstmt.setText(lineTrimmed);
		ifstmt.setLineNumber(index);
		
		String condition = matcher.group(1);
		
		ifstmt.setCondition(condition);
		
		return ifstmt;
	}

	public static ElseIfStatement buildElseIFStatements(int index, String lineTrimmed) {
		Pattern pattern = Pattern.compile("elseif(.+)then", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(lineTrimmed);
		matcher.find();
		
		ElseIfStatement ifstmt = new ElseIfStatement();
		ifstmt.setText(lineTrimmed);
		ifstmt.setLineNumber(index);
		
		String condition = matcher.group(1);
		
		ifstmt.setCondition(condition);
		
		return ifstmt;
	}

	public static ElseStatement buildElseStatements(int index,
			String lineTrimmed) {
				
		ElseStatement elsestmt = new ElseStatement();
		elsestmt.setText(lineTrimmed);
		elsestmt.setLineNumber(index);
		
		return elsestmt;
	}


	
	
}

package com.vbs.parser.domain;

import java.util.ArrayList;
import java.util.List;

public class VariableInit extends Statement {

	private DataType variableType;
	
	private List<String> variables;
	
	public VariableInit() {
		variables = new ArrayList<String>();
	}
	
	public List<String> getVariables() {
		return variables;
	}
	
	public Class<?> getType() {
		
		return this.getClass();
	}

	public DataType getVariableType() {
		return variableType;
	}

	public void setVariableType(DataType variableType) {
		this.variableType = variableType;
	}
	
}

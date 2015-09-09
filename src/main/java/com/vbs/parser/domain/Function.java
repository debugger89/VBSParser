package com.vbs.parser.domain;

import java.util.ArrayList;
import java.util.List;

import com.vbs.parser.interfaces.IContainer;
import com.vbs.parser.util.Utils;
import com.vbs.parser.workhorse.StatementFilter;

public class Function extends Statement implements IContainer{

	private String name;
		
	private List<IContainer> childContainers;
	
	private List<Statement> statements;
	
	private List<Parameter> parameters;
	
	public Function(String name){
		this.name = name;
		childContainers = new ArrayList<IContainer>();
		parameters = new ArrayList<Parameter>();
		statements = new ArrayList<Statement>();
	}
	
	
	public List<IContainer> getChildContainers() {
		return childContainers;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<Statement> filterStatements(StatementFilter filter) {
		return Utils.filterStatements(statements, filter);
	}

	public Class<?> getType() {
		return this.getClass();
	}
	
}

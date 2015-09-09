package com.vbs.parser.domain;

import java.util.ArrayList;
import java.util.List;

import com.vbs.parser.interfaces.IContainer;
import com.vbs.parser.util.Utils;
import com.vbs.parser.workhorse.StatementFilter;

public class FileContainer implements IContainer {

	private String name;
	private List<IContainer> childContainers;
	private List<Statement> statements;
	
	public FileContainer(String name) {
		this.setName(name);
		childContainers = new ArrayList<IContainer>();
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


	public List<Statement> filterStatements(StatementFilter filter) {
		
		return Utils.filterStatements(statements, filter);
		
	}

}

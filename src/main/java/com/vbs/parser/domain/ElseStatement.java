package com.vbs.parser.domain;

import java.util.ArrayList;
import java.util.List;

import com.vbs.parser.interfaces.IContainer;
import com.vbs.parser.util.Utils;
import com.vbs.parser.workhorse.StatementFilter;

public class ElseStatement extends Statement implements IContainer {

	private List<IContainer> childContainers;
	
	private List<Statement> statements;
	
	public ElseStatement(){
		childContainers = new ArrayList<IContainer>();
		statements = new ArrayList<Statement>();
	}
	
	
	public List<IContainer> getChildContainers() {
		return childContainers;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public List<Statement> filterStatements(StatementFilter filter) {
		return Utils.filterStatements(statements, filter);
	}

	@Override
	public Class<?> getType() {
		return this.getClass();
	}

}

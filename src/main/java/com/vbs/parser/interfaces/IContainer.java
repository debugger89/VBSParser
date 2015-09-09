package com.vbs.parser.interfaces;

import java.util.List;

import com.vbs.parser.domain.Statement;
import com.vbs.parser.workhorse.StatementFilter;

public interface IContainer{

	public List<IContainer> getChildContainers();
	
	public List<Statement> getStatements();
	
	public List<Statement> filterStatements(StatementFilter filter);
	
}

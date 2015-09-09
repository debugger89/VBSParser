package com.vbs.parser.workhorse;

@SuppressWarnings("rawtypes")
public class StatementFilter {

	private Class filterClass;
	
	public StatementFilter (Class clz) {
		this.filterClass = clz;
	}

	public Class getFilterClass() {
		return filterClass;
	}
	
}

package com.vbs.parser.domain;

import com.vbs.parser.interfaces.IContainer;

public class VBStatement extends Statement {

	private String text;
	
	private IContainer parent;
	
	private int lineNumber;
	
	public Class<?> getType() {
		
		return this.getClass();
	}

	public String getText() {
		
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public IContainer getParentContainer() {
		return parent;
	}
	
	public void setParentContainer(IContainer parent) {
		this.parent = parent;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}

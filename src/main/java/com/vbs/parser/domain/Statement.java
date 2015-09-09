package com.vbs.parser.domain;

import com.vbs.parser.interfaces.IContainer;

public abstract class Statement {

	private int lineNumber;
	
	private IContainer parent;
	
	private String text;
	
	public abstract Class<?> getType();

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public IContainer getParent() {
		return parent;
	}

	public void setParent(IContainer parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

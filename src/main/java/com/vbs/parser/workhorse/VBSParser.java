package com.vbs.parser.workhorse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.vbs.parser.domain.Constants;
import com.vbs.parser.domain.ElseIfStatement;
import com.vbs.parser.domain.ElseStatement;
import com.vbs.parser.domain.EmptyLineStatement;
import com.vbs.parser.domain.FileContainer;
import com.vbs.parser.domain.Function;
import com.vbs.parser.domain.IfStatement;
import com.vbs.parser.domain.Statement;
import com.vbs.parser.domain.VBStatement;
import com.vbs.parser.domain.LoopStatement;
import com.vbs.parser.domain.VariableInit;
import com.vbs.parser.interfaces.IContainer;
import com.vbs.parser.util.Utils;

public class VBSParser {

	final static Logger logger = Logger.getLogger(VBSParser.class);
	
	private File vbsFile;
	
	private FileContainer fileContainer;
	
	private List<IContainer> containerStack;
	
	private List<String> sourceLines; 
	
	private List<Statement> flatStructuredStatements;
		
	public VBSParser(File vbsFile) {

		try {
			checkFilExistance(vbsFile);
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
		this.vbsFile = vbsFile;
		flatStructuredStatements = new ArrayList<Statement>();
		fileContainer = new FileContainer(vbsFile.getName());
		containerStack = new ArrayList<IContainer>();
		containerStack.add(fileContainer);
	}
	
	public void parse() {
		sourceLines = Utils.getLines(vbsFile);
		for(int i=0; i<sourceLines.size() ; i++){
			
			String line = sourceLines.get(i);
			identifyAndConvert(i, line);
			
		}
	}
	

	private void identifyAndConvert(final int index, final String line) {
		
		String lineTrimmed = line.trim();
		
		if(lineTrimmed.toLowerCase().startsWith(Constants.FUNCTION_IDENTIFIER)) {
			Function func = StatementFactory.buildFunctionStatements(index, lineTrimmed);
			
			IContainer parent = getLastContainerFromStack();
			func.setParent(parent);
			parent.getStatements().add(func);
			parent.getChildContainers().add(func);
			containerStack.add(func);
			
			flatStructuredStatements.add(func);
			
		}  else if(lineTrimmed.toLowerCase().startsWith(Constants.DIM_IDENTIFIER)){
			
			VariableInit vinit = StatementFactory.buildVariableInitStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			vinit.setParent(parent);
			parent.getStatements().add(vinit);
			
			flatStructuredStatements.add(vinit);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.WHILE_IDENTIFIER) 
				|| lineTrimmed.toLowerCase().startsWith(Constants.DO_WHILE_IDENTIFIER) 
				|| lineTrimmed.toLowerCase().startsWith(Constants.FOR_IDENTIFIER)){
			
			LoopStatement loop = StatementFactory.buildLoopStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			loop.setParent(parent);
			parent.getStatements().add(loop);
			parent.getChildContainers().add(loop);
			containerStack.add(loop);
			
			flatStructuredStatements.add(loop);
			
		} else if(lineTrimmed.toLowerCase().matches(Constants.END_LOOP_REGEX)) {
			
			VBStatement end = StatementFactory.buildGenericStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			end.setParentContainer(parent);
			parent.getStatements().add(end);
			
			
			popLastContainerFromStack();
			
			flatStructuredStatements.add(end);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.IF_IDENTIFIER)) {
			
			IfStatement ifstmt = StatementFactory.buildIFStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			ifstmt.setParent(parent);
			parent.getStatements().add(ifstmt);
			parent.getChildContainers().add(ifstmt);
			containerStack.add(ifstmt);
			
			flatStructuredStatements.add(ifstmt);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.ELSE_IF_IDENTIFIER)) {
			
			popLastContainerFromStack();
			
			ElseIfStatement elseifstmt = StatementFactory.buildElseIFStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			elseifstmt.setParent(parent);
			parent.getStatements().add(elseifstmt);
			parent.getChildContainers().add(elseifstmt);
			containerStack.add(elseifstmt);
			
			flatStructuredStatements.add(elseifstmt);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.ELSE_IDENTIFIER)) {
			
			popLastContainerFromStack();
			
			ElseStatement elsestmt = StatementFactory.buildElseStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			elsestmt.setParent(parent);
			parent.getStatements().add(elsestmt);
			parent.getChildContainers().add(elsestmt);
			containerStack.add(elsestmt);
			
			flatStructuredStatements.add(elsestmt);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.END_IF_REGEX)) {
			
			popLastContainerFromStack();
			
			VBStatement end = StatementFactory.buildGenericStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			end.setParentContainer(parent);
			parent.getStatements().add(end);
						
			flatStructuredStatements.add(end);
			
		} else if(lineTrimmed.toLowerCase().startsWith(Constants.END_FUNCTION_IDENTIFIER)){
			
			VBStatement end = StatementFactory.buildGenericStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			end.setParentContainer(parent);
			parent.getStatements().add(end);
			
			// Regex meaning: 
			//	- end matches the characters end literally (case sensitive)
			//	- <space>+ matches the character <space> literally
			//	- [a-zA-Z]+ match a single or multiple characters present
			//	- $ assert position at end of the string
			Pattern pattern = Pattern.compile("end +[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(line);
			if(matcher.find()) {
				popLastContainerFromStack();
			}
			
			flatStructuredStatements.add(end);
			
		}  else if(lineTrimmed.isEmpty()){
			
			EmptyLineStatement emptystmt = new EmptyLineStatement();
			emptystmt.setText("");
			emptystmt.setLineNumber(index);
			IContainer parent = getLastContainerFromStack();
			emptystmt.setParent(parent);
			parent.getStatements().add(emptystmt);
			
			flatStructuredStatements.add(emptystmt);
			
		}	else {
			
			VBStatement gstmt = StatementFactory.buildGenericStatements(index, lineTrimmed);
			IContainer parent = getLastContainerFromStack();
			gstmt.setParentContainer(parent);
			parent.getStatements().add(gstmt);
			
			flatStructuredStatements.add(gstmt);
			
		}
	}

	private void checkFilExistance(File vbsFile) throws FileNotFoundException {

		if (!vbsFile.exists()) {
			throw new FileNotFoundException("File not found. " + vbsFile);
		}

	}
	
	private IContainer getLastContainerFromStack(){
		return containerStack.get(containerStack.size() - 1);
	}
	
	private void popLastContainerFromStack(){
		
		if(containerStack.size() > 1) {
			containerStack.remove(containerStack.size() - 1);
		}
	}

	public List<String> getSourceLines() {
		return sourceLines;
	}

	public FileContainer getContainer() {
		return fileContainer;
	}

	public void setContainer(FileContainer container) {
		this.fileContainer = container;
	}
	
	public List<String> getParsedStatementLines(){
		List<String> parsedLines = new ArrayList<String>();
		
		parsedLines = visitContainersAndExtractRecursively(parsedLines, fileContainer);
		
		return parsedLines;
	}
	
	public List<Statement> getParsedStatements(){
		
		return flatStructuredStatements;
	}
	
	private List<String> visitContainersAndExtractRecursively(List<String> stmtList, IContainer container){
		
		List<String> parsedStmtList = stmtList;
		
		List<Statement> statementsInContainer = container.getStatements();
		
		for(Statement st : statementsInContainer){
			if(st instanceof IContainer) {
				parsedStmtList.add(st.getText());
				visitContainersAndExtractRecursively(parsedStmtList, (IContainer)st);
			} else{
				parsedStmtList.add(st.getText());
			}
		}
		
		return parsedStmtList;
		
	}
	

}

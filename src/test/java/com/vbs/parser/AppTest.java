package com.vbs.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;

import com.vbs.parser.domain.Function;
import com.vbs.parser.domain.Statement;
import com.vbs.parser.util.Utils;
import com.vbs.parser.workhorse.StatementFilter;
import com.vbs.parser.workhorse.VBSParser;

/**
 * Unit test for vbsparser App.
 */
public class AppTest extends TestCase
{
    public void testApp() throws FileNotFoundException
    {
        VBSParser parser = new VBSParser(new File("test.vbs"));
        
        parser.parse();
        
        List<Statement> fList = parser.getContainer().filterStatements(new StatementFilter(Function.class));
               
        parser.getParsedStatementLines().add(fList.get(0).getLineNumber() +1, "new line added here");
               
        Utils.writeLinesToFile( parser.getParsedStatementLines(), new File("result.vbs"));
    }
}

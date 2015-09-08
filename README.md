# VBSParser
A VBScript Parser with file generation support.


# Examples : 

Parse the vbs file.
```java
VBSParser parser = new VBSParser(new File("test.vbs"));
parser.parse();
```

Filtering the statements.

```java
List<Statement> fList = parser.getContainer().filterStatements(new StatementFilter(Function.class));
parser.parse();
```

Adding a new lines.

```java
parser.getParsedStatementLines().add(fList.get(0).getLineNumber() +1, "new line added here");
parser.parse();
```

Generating with the changes made.

```java
Utils.writeLinesToFile( parser.getParsedStatementLines(), new File("result.vbs"));
parser.parse();
```

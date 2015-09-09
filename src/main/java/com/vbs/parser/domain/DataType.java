package com.vbs.parser.domain;

public enum DataType {

	STRING("String"),
	INT("Int"),
	VAR("var"),
	ByVal("ByVal"),
	ByRef("ByRef");
	
	private final String name;       

    private DataType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
    
    public static DataType fromString(String text) {
        if (text != null) {
          for (DataType b : DataType.values()) {
            if (text.equalsIgnoreCase(b.name)) {
              return b;
            }
          }
        }
        return VAR;
      }
}

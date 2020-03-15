package meru.application.designer.builder;

public enum EnumBoolean {
  
  FALSE(false, "'N'", "\"N\""),
  TRUE(true, "'Y'", "\"Y\"");

  private boolean booleanValue;
  private String booleanSQLString;
  private String booleanJavaString;


  private EnumBoolean(boolean booleanValue,
                      String booleanSQLString,
                      String booleanJavaString) {

    this.booleanValue = booleanValue;
    this.booleanSQLString = booleanSQLString;
    this.booleanJavaString = booleanJavaString;
  }


  public boolean booleanValue() {

    return this.booleanValue;
  }


  public String booleanSQLString() {

    return this.booleanSQLString;
  }


  public String booleanJavaString() {

    return this.booleanJavaString;
  }


  public static EnumBoolean getEnumBoolean(boolean value) {

    return (value) ? TRUE : FALSE;
  }
}
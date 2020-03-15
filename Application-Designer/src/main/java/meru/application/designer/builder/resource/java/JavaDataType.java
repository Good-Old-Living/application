package meru.application.designer.builder.resource.java;

import java.util.HashMap;
import java.util.Map;

import meru.application.designer.domain.model.DataType;

public class JavaDataType {
  
  private String mType;
  private DataType mXMLDataType;
  
  private static Map<DataType, JavaDataType> mDataTypes = new HashMap<DataType, JavaDataType>();

  
  public static final JavaDataType STRING = new JavaDataType("java.lang.String",
                                                             DataType.getDataType("string"));
  public static final JavaDataType BOOLEAN = new JavaDataType("Boolean",
                                                              DataType.getDataType("boolean"));
  public static final JavaDataType BYTE = new JavaDataType("Byte",
                                                           DataType.getDataType("byte"));
  public static final JavaDataType SHORT = new JavaDataType("Short",
                                                            DataType.getDataType("short"));
  public static final JavaDataType INT = new JavaDataType("Integer",
                                                          DataType.getDataType("int"));
  public static final JavaDataType LONG = new JavaDataType("Long",
                                                           DataType.getDataType("long"));
  public static final JavaDataType FLOAT = new JavaDataType("Float",
                                                            DataType.getDataType("float"));
  public static final JavaDataType DOUBLE = new JavaDataType("Double",
                                                             DataType.getDataType("double"));
  public static final JavaDataType DATE = new JavaDataType("java.util.Date",
                                                           DataType.getDataType("date"));
  public static final JavaDataType DATE_TIME = new JavaDataType("java.util.Calendar",
                                                                DataType.getDataType("dateTime"));
  public static final JavaDataType BINARY = new JavaDataType("byte[]",
                                                             DataType.getDataType("base64Binary"));

  static {
    mDataTypes.put(DataType.getDataType("boolean"),
                   BOOLEAN);

    mDataTypes.put(DataType.getDataType("byte"),
                   BYTE);
    mDataTypes.put(DataType.getDataType("short"),
                   SHORT);
    mDataTypes.put(DataType.getDataType("int"),
                   INT);
    mDataTypes.put(DataType.getDataType("integer"),
                   INT);
    mDataTypes.put(DataType.getDataType("long"),
                   LONG);
    mDataTypes.put(DataType.getDataType("float"),
                   FLOAT);
    mDataTypes.put(DataType.getDataType("double"),
                   DOUBLE);

    mDataTypes.put(DataType.getDataType("date"),
                   DATE);
    mDataTypes.put(DataType.getDataType("dateTime"),
                   DATE_TIME);

    mDataTypes.put(DataType.getDataType("string"),
                   STRING);

    mDataTypes.put(DataType.getDataType("base64Binary"),
                   BINARY);
  }


  private JavaDataType(String type, DataType xmlDataType) {

    if (type == null || type.trim().length() == 0) {
      throw new IllegalArgumentException("type = '"+type+"'");
    }

    this.mType = type;
    this.mXMLDataType = xmlDataType;
  }


  public String getType() {

    return this.mType;
  }


  public DataType getDataType() {

    return this.mXMLDataType;
  }


  public boolean isEnum() {

    return this.mXMLDataType.isEnum();
  }


  public static JavaDataType getJavaDataType(DataType xmlDataType) {

    JavaDataType dataType = (JavaDataType) mDataTypes.get(xmlDataType);

    if (dataType == null) {
      dataType = getUserDefinedType(xmlDataType);
    }

    return dataType;
  }


  private static JavaDataType getUserDefinedType(DataType xmlDataType) {

    JavaDataType dataType = new JavaDataType(xmlDataType.getNamespace()+"."+xmlDataType.getType(), xmlDataType);

    return dataType;
  }
}
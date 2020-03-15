package meru.application.designer.builder.resource.db.mysql;

import java.util.HashMap;
import java.util.Map;

import meru.application.designer.domain.model.DataType;

public class MySQLDataType {

  private String mType;
  private DataType mXMLDataType;

  private static Map<DataType, MySQLDataType> mDataTypes = new HashMap<DataType, MySQLDataType>();

  public static final MySQLDataType ENTITYID = new MySQLDataType("binary[16]",
                                                                 DataType.getDataType("EntityId"));

  public static final MySQLDataType STRING = new MySQLDataType("varchar",
                                                               DataType.getDataType("string"));
  public static final MySQLDataType BOOLEAN = new MySQLDataType("varchar(1)",
                                                                DataType.getDataType("boolean"));
  public static final MySQLDataType BYTE = new MySQLDataType("tinyint",
                                                             DataType.getDataType("byte"));
  public static final MySQLDataType SHORT = new MySQLDataType("smallint",
                                                              DataType.getDataType("short"));
  public static final MySQLDataType INT = new MySQLDataType("int",
                                                            DataType.getDataType("int"));
  public static final MySQLDataType INTEGER = new MySQLDataType("int",
                                                                DataType.getDataType("integer"));
  public static final MySQLDataType LONG = new MySQLDataType("bigint",
                                                             DataType.getDataType("long"));
  public static final MySQLDataType FLOAT = new MySQLDataType("float",
                                                              DataType.getDataType("float"));
  public static final MySQLDataType DOUBLE = new MySQLDataType("double",
                                                               DataType.getDataType("double"));
  public static final MySQLDataType DATE = new MySQLDataType("date",
                                                             DataType.getDataType("date"));
  public static final MySQLDataType DATE_TIME = new MySQLDataType("timestamp",
                                                                  DataType.getDataType("dateTime"));
  public static final MySQLDataType BINARY = new MySQLDataType("blob",
                                                               DataType.getDataType("base64Binary"));

  public static final MySQLDataType TEXT = new MySQLDataType("text",
                                                             DataType.getDataType("string"));

  static {

    mDataTypes.put(DataType.getDataType("EntityId"),
                   ENTITYID);

    mDataTypes.put(DataType.getDataType("boolean"),
                   BOOLEAN);

    mDataTypes.put(DataType.getDataType("byte"),
                   BYTE);
    mDataTypes.put(DataType.getDataType("short"),
                   SHORT);
    mDataTypes.put(DataType.getDataType("int"),
                   INT);
    mDataTypes.put(DataType.getDataType("integer"),
                   INTEGER);
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

  private MySQLDataType(String type,
                        DataType xmlDataType) {

    this.mType = type;
    this.mXMLDataType = xmlDataType;
  }

  public String getType() {

    return this.mType;
  }

  public DataType getDataType() {

    return this.mXMLDataType;
  }

  public static MySQLDataType getSQLDataType(DataType xmlDataType) {

    if (xmlDataType.isEnum()) {
      return STRING;
    }

    if ((xmlDataType.isComplexType()) || (xmlDataType.isComplexEnum())) {
      return LONG;
    }

    MySQLDataType dataType = (MySQLDataType) mDataTypes.get(xmlDataType);

    if (dataType == null) {
      dataType = getUserDefinedType(xmlDataType);
    }

    return dataType;
  }

  private static MySQLDataType getUserDefinedType(DataType xmlDataType) {

    MySQLDataType dataType = new MySQLDataType(xmlDataType.getType(),
                                               xmlDataType);

    return dataType;
  }
}
package meru.application.designer.domain.model;

public class DataType {

    public static final DataType BOOLEAN = new DataType("boolean");
    public static final DataType INT = new DataType("int");
    public static final DataType LONG = new DataType("long");
    public static final DataType FLOAT = new DataType("float");
    public static final DataType DOUBLE = new DataType("double");
    public static final DataType STRING = new DataType("string");
    public static final DataType DATE = new DataType("date");
    public static final DataType DATE_TIME = new DataType("dateTime");
    public static final DataType BINARY = new DataType("binary");
    public static final DataType ENTITYID = new DataType("EntityId");
    private String mNamespace;
    private String mXMLType;
    private AttributeType mEntityType;

    public DataType(String xmlType) {

        this(xmlType,
             AttributeType.SIMPLE);
    }

    public DataType(String xmlType,
                    AttributeType entityType) {

        this(null,
             xmlType,
             entityType);
    }

    public DataType(String namespace,
                    String xmlType,
                    AttributeType entityType) {

        if (xmlType == null || xmlType.trim()
                                      .length() == 0) {
            throw new IllegalArgumentException("Invalid value : '" + xmlType
                    + "'");
        }

        mNamespace = (namespace == null || namespace.trim()
                                                    .length() == 0) ? null
                : namespace;
        mXMLType = xmlType;
        mEntityType = entityType;
    }

    public String getNamespace() {

        return mNamespace;
    }

    public String getType() {

        return mXMLType;
    }

    public boolean isComplexType() {

        return mEntityType == AttributeType.COMPLEX;
    }

    public boolean isSimpleType() {

        return mEntityType == AttributeType.SIMPLE;
    }

    public boolean isEnum() {

        return mEntityType == AttributeType.SIMPLE_ENUM;
    }

    public boolean isBoolean() {

        return this == BOOLEAN;
    }

    public boolean isComplexEnum() {

        return mEntityType == AttributeType.COMPLEX_ENUM;
    }

    public boolean isSame(String type) {

        if (type == null) {
            return false;
        }

        return mXMLType.equals(type);
    }

    public boolean isFacetApplicable(Facet facet) {

        if (this == STRING) {
            return "maxLength".equals(facet.getName());
        }

        return true;
    }

    public boolean equals(Object type) {

        return (type instanceof DataType)
                && (((DataType) type).mXMLType.equals(mXMLType));
    }

    public int hashCode() {

        return mXMLType.hashCode();
    }

    public static DataType getDataType(String type) {

        if (type.equals("string")) {
            return STRING;
        }
        
        if (type.equals("EntityId")) {
          return STRING;
      }
        
        if ((type.equals("int")) || (type.equals("integer"))) {
            return INT;
        }
        if (type.equals("long")) {
            return LONG;
        }
        if (type.equals("float")) {
            return FLOAT;
        }
        if (type.equals("double")) {
            return DOUBLE;
        }
        if (type.equals("date")) {
            return DATE;
        }
        if (type.equals("dateTime")) {
            return DATE_TIME;
        }
        if (type.equals("boolean")) {
            return BOOLEAN;
        }
        if (type.equals("base64Binary")) {
            return BINARY;
        }

        return new DataType(type,
                            AttributeType.SIMPLE_ENUM);
    }
}
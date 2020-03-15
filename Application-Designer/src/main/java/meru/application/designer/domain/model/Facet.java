package meru.application.designer.domain.model;

public class Facet {

    public static final String MAX_LENGTH = "maxLength";

    private String mName;
    private Object mValue;

    public Facet(String name,
                 Object value) {

        if (name == null || name.trim()
                                .length() == 0) {
            throw new IllegalArgumentException("Invalid value : '" + name + "'");
        }

        mName = name;
        mValue = value;
    }

    public String getName() {

        return mName;
    }

    public Object getValue() {

        return mValue;
    }

    public String toString() {

        return "Facet[" + mName + " = " + mValue + "]";
    }
}
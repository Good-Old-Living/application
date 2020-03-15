package meru.application.designer.domain.model;

public class Argument {
    private String mName;
    private DataType mDataType;
    private boolean mIsMultiValued;

    public Argument(DataType dataType,
                    String name) {

        this(dataType,
             name,
             false);
    }

    public Argument(DataType dataType,
                    String name,
                    boolean isMultiValued) {

        this.mName = name;
        this.mDataType = dataType;
        this.mIsMultiValued = isMultiValued;
    }

    public String getName() {

        return this.mName;
    }

    public DataType getDataType() {

        return this.mDataType;
    }

    public boolean isMultiValued() {

        return this.mIsMultiValued;
    }

    public String toString() {

        return "Argument[" + this.mDataType + " " + this.mName + "]";
    }
}
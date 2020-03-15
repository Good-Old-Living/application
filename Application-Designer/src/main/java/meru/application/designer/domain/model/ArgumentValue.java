package meru.application.designer.domain.model;

public class ArgumentValue extends Argument {

    private Object mValue;

    public ArgumentValue(DataType dataType,
                         String name,
                         Object value) {

        super(dataType,
              name);
        this.mValue = value;
    }

    public Object getValue() {

        return this.mValue;
    }

    public String toString() {

        return super.toString() + " = " + this.mValue;
    }
}
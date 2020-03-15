package meru.application.designer.domain.model;

import java.util.ArrayList;
import java.util.List;

public class EnumValue {

    private String mName;
    private List<ArgumentValue> mArgValues;

    public EnumValue(String name) {

        this(name,
             null);
    }

    public EnumValue(String name,
                     List<ArgumentValue> argValues) {

        mName = name;
        mArgValues = argValues;
    }

    public String getName() {

        return mName;
    }

    public List<ArgumentValue> getArgumentValues() {

        return mArgValues;
    }

    public void addArgumentValue(ArgumentValue argValue) {

        if (mArgValues == null) {
            mArgValues = new ArrayList<ArgumentValue>();
        }
        mArgValues.add(argValue);
    }

    public String toString() {

        StringBuilder strBuilder = new StringBuilder(mName);

        if (mArgValues != null) {
            for (ArgumentValue argValue : mArgValues) {
                strBuilder.append("{ ")
                          .append(argValue)
                          .append("} ");
            }
        }

        return strBuilder.toString();
    }
}
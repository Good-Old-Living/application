package meru.application.designer.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute implements Cloneable {
    private String mName;
    private DataType mDataType;
    private Visibility mVisibility;
    private String mDefaultValue;
    private boolean mIsId;
    private boolean mIsNullable;
    private boolean mIsReference;
    private boolean mIsUnbounded;
    private boolean mIsRequired;
    private boolean mIsUnique;
    private Map<String, Facet> mFacetMap;
    private Map<String, String> mAttributes;
    private boolean mIsTransient;

    public Attribute(Visibility visibility,
                     DataType type,
                     String name) {

        if (type == null) {
            throw new NullPointerException("Null 'DataType' passed");
        }

        if (name == null) {
            throw new NullPointerException("Null 'Name' passed");
        }

        this.mName = name;
        this.mDataType = type;
        this.mVisibility = visibility;

        if (name.equals("id")) {
            mIsId = true;
        }

    }

    public void addAttribute(String name,
                             String value) {

        if (this.mAttributes == null) {
            this.mAttributes = new HashMap<String, String>(1);
        }

        this.mAttributes.put(name,
                             value);
    }

    public String getAttribute(String name) {

        if (this.mAttributes == null) {
            return null;
        }

        return (String) this.mAttributes.get(name);
    }

    public void setName(String name) {

        this.mName = name;
    }

    public String getName() {

        return this.mName;
    }

    public DataType getDataType() {

        return this.mDataType;
    }

    public Visibility getVisibility() {

        return this.mVisibility;
    }

    public void setDefaultValue(String defaultValue) {

        if ((defaultValue != null) && (defaultValue.trim()
                                                   .length() == 0)) {
            this.mDefaultValue = null;
        }
        else
            this.mDefaultValue = defaultValue;
    }

    public String getDefaultValue() {

        return this.mDefaultValue;
    }

    public void setTransient(boolean isTransient) {

        this.mIsTransient = isTransient;
    }

    public boolean isTransient() {

        return this.mIsTransient;
    }

    public void setMultiValued(boolean multiValued) {

        this.mIsUnbounded = multiValued;
    }

    public boolean isMultiValued() {

        return this.mIsUnbounded;
    }

    public void setRequired(boolean required) {

        mIsRequired = required;
    }

    public boolean isRequired() {

        return this.mIsRequired;
    }

    public void setUnique(boolean isUnique) {

        mIsUnique = isUnique;
    }

    public boolean isUnique() {

        return mIsUnique;
    }

    public void setNullable(boolean isNullable) {

        this.mIsNullable = isNullable;
    }

    public boolean isNullable() {

        return this.mIsNullable;
    }

    public void setId(boolean isId) {

        mIsId = isId;
    }

    public boolean isId() {

        return mIsId;
    }

    public boolean isBooleanType() {

        return this.mDataType.isBoolean();
    }

    public boolean isComplexType() {

        return this.mDataType.isComplexType();
    }

    public boolean isComplexEnumType() {

        return this.mDataType.isComplexEnum();
    }

    public boolean isDateType() {

        return (this.mDataType == DataType.DATE)
                || (this.mDataType == DataType.DATE_TIME);
    }

    public void setReference(boolean isReference) {

        this.mIsReference = isReference;
    }

    public boolean isReference() {

        return this.mIsReference;
    }

    public boolean isOwningAttribute() {

        return (isComplexType()) && (!isReference());
    }

    public String getAttributeBeanName() {

        return Character.toUpperCase(this.mName.charAt(0))
                + this.mName.substring(1);
    }

    public void addFacet(Facet facet) {

        if (this.mFacetMap == null) {
            this.mFacetMap = new HashMap<String, Facet>();
        }

        this.mFacetMap.put(facet.getName(),
                           facet);
    }

    public void setFacets(List<Facet> facets) {

        if (facets != null)
            for (Facet facet : facets)
                addFacet(facet);
    }

    public Facet getFacet(String name) {

        if (this.mFacetMap == null) {
            return null;
        }

        return (Facet) this.mFacetMap.get(name);
    }

    public String getDatabaseColumnName() {

        String name = getName();

        name = getDatabaseName(name);

        if (isComplexType()) {
            name = name + "_id";
        }

        return name;
    }

    public static String getDatabaseName(String name) {

        char[] chars = new char[name.length() - 1];
        name.getChars(1,
                      name.length(),
                      chars,
                      0);

        StringBuilder strBuilder = new StringBuilder(10);

        strBuilder.append(Character.toLowerCase(name.charAt(0)));

        for (char ch : chars) {
            if (Character.isUpperCase(ch)) {
                strBuilder.append('_')
                          .append(Character.toLowerCase(ch));
            }
            else {
                strBuilder.append(ch);
            }
        }

        return strBuilder.toString();
    }

    public String toString() {

        return this.mVisibility + " " + this.mDataType + " " + this.mName;
    }

    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
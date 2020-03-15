package meru.application.designer.domain.model.character;

public abstract class Characteristic<T> {

    protected String mName;
    protected T mValue;

    public Characteristic(String name,
                          T value) {
        mName = name;
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public T getValue() {
        return mValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        return this.getClass()
                   .isAssignableFrom(object.getClass())
                        && ((Characteristic<T>) object).mName.equals(mName)
                        && valueEquals(((Characteristic<T>) object).mValue);
    }

    protected boolean valueEquals(T value) {
        return mValue.equals(value);
    }

    public String toString() {
        return mName + " : " + mValue;
    }

}

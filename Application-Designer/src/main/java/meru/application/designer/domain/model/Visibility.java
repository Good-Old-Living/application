package meru.application.designer.domain.model;

public enum Visibility {

    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public");

    private String mLabel;

    private Visibility(String label) {

        mLabel = label;
    }

    public String toString() {

        return mLabel;
    }
}
package meru.application.designer.domain.model;

public class EntityInterest {

    private String mPackage;
    private String mEntityName;
    private Object mUserObject;

    public EntityInterest(String packageName,
                          String entityName,
                          Object userObject) {

        this.mPackage = packageName;
        this.mEntityName = entityName;
        this.mUserObject = userObject;
    }

    public String getPackage() {

        return this.mPackage;
    }

    public String getEntityName() {

        return this.mEntityName;
    }

    public Object getUserObject() {

        return this.mUserObject;
    }
}
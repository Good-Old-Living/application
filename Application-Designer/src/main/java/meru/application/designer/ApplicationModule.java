package meru.application.designer;

public class ApplicationModule {

    public static final String PROP_DATABASE_TABLE_PREFIX = "database-table-prefix";
    public static final String ENTITY_ANNOTATION_NAMESPACE = "app.entity.annotation";
    
    private String id;
    private String name;

    public ApplicationModule() {

    }

    public ApplicationModule(String id,
                       String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

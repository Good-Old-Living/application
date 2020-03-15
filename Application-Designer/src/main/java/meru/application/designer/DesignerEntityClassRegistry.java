package meru.application.designer;

import meru.app.registry.EntityClassRegistry;

public class DesignerEntityClassRegistry extends EntityClassRegistry {

    protected void populateClassMap() {

        addResourceClass("ApplicationModule",
                         ApplicationModule.class);
        addResourceClass("Application",
                         Application.class);
    }

}

package meru.app.service;

import meru.app.engine.entity.EntityAppEngine;
import meru.app.service.ServiceManager;

public abstract class AbstractAppService implements AppService {

    protected EntityAppEngine appEngine;
    protected ServiceManager serviceManager;

    public void setContext(EntityAppEngine appEngine,
                           ServiceManager serviceManager) {

        this.appEngine = appEngine;
        this.serviceManager = serviceManager;
    }

}

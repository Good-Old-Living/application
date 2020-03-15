package meru.application.designer;

import meru.app.AppRequest;
import meru.app.engine.entity.AbstractEntityLifeCycle;

public class ApplicationModuleLifeCycle extends
        AbstractEntityLifeCycle<ApplicationModule> {

    @Override
    public ApplicationModule postGet(ApplicationModule applicationModule) {

        if (AppRequest.currentRequest()
                      .existsParameter("deploy")) {

            ApplicationDeployer appDeployer = new ApplicationDeployer(appConfig,
                                                                      applicationModule);
            appDeployer.deploy();
        }

        return applicationModule;

    }

}

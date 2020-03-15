package meru.application.designer;

import java.io.File;

import meru.app.config.AppConfig;

import meru.application.designer.builder.BuilderConfig;
import meru.application.designer.builder.DomainModelBuilder;

public class ApplicationDeployer {

    private AppConfig mAppConfig;
    private ApplicationModule mAppModule;

    public ApplicationDeployer(AppConfig appConfig, ApplicationModule appModule) {
        mAppConfig = appConfig;
        mAppModule = appModule;
    }

    public void deploy() {

        File appRoot = new File(mAppConfig.getProperty("applications-home"));

       /* File appHome = new File(appsHome,
                                mAppModule.getId());*/

        BuilderConfig config = new BuilderConfig(mAppConfig, appRoot, mAppModule.getId());

        DomainModelBuilder builder = new DomainModelBuilder(config);
        builder.build();


    }

}

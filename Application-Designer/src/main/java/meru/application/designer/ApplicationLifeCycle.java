package meru.application.designer;

import java.io.IOException;

import meru.app.AppRequest;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.io.FileMerger;

public class ApplicationLifeCycle extends AbstractEntityLifeCycle<Application> {

    @Override
    public Application postGet(Application application) {

        AppRequest appRequest = AppRequest.currentRequest();
        if (appRequest.existsParameter("deploy")) {

            ApplicationDeployer appDeployer = new ApplicationDeployer(appConfig,
                                                                      application);
            appDeployer.deploy();
        } else if (appRequest.existsParameter("mergeFiles")) {

            String webHome = "D:/application/apache-tomcat-8.0.15";
            String app = appRequest.getSingleParameter("a");
            if (app == null) {

                throw new RuntimeException("Web application name not provided");

            }

            try {

                FileMerger.mergeJS(webHome
                                           + "/webapps/faces/merge/js-merge.txt",
                                   webHome + "/webapps/" + app
                                           + "/st/js/app.js");
                FileMerger.mergeJS(webHome + "/webapps/" + app
                                           + "/st/js/merge/js-merge.txt",
                                   webHome + "/webapps/" + app
                                           + "/st/js/app.js",
                                   true);
                FileMerger.mergeCSS(webHome
                                            + "/webapps/faces/merge/css-merge.txt",
                                    webHome + "/webapps/" + app
                                            + "/st/css/app.css");
                FileMerger.mergeJS(webHome + "/webapps/" + app
                                           + "/st/css/merge/css-merge.txt",
                                   webHome + "/webapps/" + app
                                           + "/st/css/app.css",
                                   true);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return application;

    }

}

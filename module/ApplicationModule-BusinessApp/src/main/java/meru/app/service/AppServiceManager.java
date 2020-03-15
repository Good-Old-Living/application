package meru.app.service;

import meru.app.engine.entity.EntityAppEngine;
import meru.app.service.schedule.SchedulerService;
import meru.comm.mail.MailBox;

public class AppServiceManager extends ServiceManager {

  protected EntityAppEngine appEngine;

  public void setContext(String appName,
                         EntityAppEngine appEngine) {
    setAppName(appName);
    this.appEngine = appEngine;
  }

  public void startServices() {

    String property = mAppProperties.getProperty("app.schedule.enabled");

    if (property != null && property.equalsIgnoreCase("true")) {

      addService(ServiceManager.MAIL_BOX_NAME,
                 new MailBox(mAppProperties));

      try {
        SchedulerService schedulerService = new SchedulerService();
        schedulerService.setContext(appEngine,
                                    this);

        addService(SchedulerService.NAME,
                   schedulerService);
        schedulerService.start();

      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    }

  }

}

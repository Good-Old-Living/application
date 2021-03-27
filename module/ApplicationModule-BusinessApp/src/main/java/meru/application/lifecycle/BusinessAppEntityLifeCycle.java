package meru.application.lifecycle;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import app.domain.AppEntityState;
import meru.app.AppProperty;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.app.service.ServiceManager;
import meru.comm.mobile.http.HttpSMSSender;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.sys.IOSystem;
import meru.template.TemplateEngine;
import meru.template.TemplateSingleData;

public abstract class BusinessAppEntityLifeCycle<T> extends AbstractEntityLifeCycle<T> {

  private static final Map<String, String> templateMap = new HashMap<>(2);

  protected AppEntityState getAppEntityState(String entityName,
                                             int code) {
    EntityQuery<AppEntityState> entityQuery = appEngine.createQuery(AppEntityState.class);
    entityQuery.addQueryParameter("entity",
                                  entityName);

    entityQuery.addQueryParameter("code",
                                  code);

    AppEntityState appState = appEngine.getFirst(entityQuery);
    if (appState == null) {
      throw new AppException("Unknown AppEntityState [" + entityName + ", " + code + "]");
    }

    return appState;

  }

  protected void sendSMS(String templateFile,
                         String mobileNo,
                         Object data) {
    sendSMS(templateFile,
            null,
            mobileNo,
            data);

  }

  protected void sendSMS(String templateFile,
                         String smsTemplateId,
                         String mobileNo,
                         Object data) {

    HttpSMSSender smsSender = serviceManager.getService(ServiceManager.SMS_SENDER,
                                                        HttpSMSSender.class);

    String templateFilePath = AppProperty.TEMPLATE_DIR_SMS + templateFile;
    String templateText = templateMap.get(templateFilePath);

    if (templateText == null) {
      templateText = loadTemplate(templateFilePath);
    }

    TemplateSingleData templateData = new TemplateSingleData(data);
    String message = TemplateEngine.getText(templateText,
                                            templateData);

    smsSender.send(smsTemplateId,
                   mobileNo,
                   message);

  }

  private synchronized String loadTemplate(String templateFilePath) {

    String templateText = templateMap.get(templateFilePath);
    if (templateText == null) {

      templateText = IOSystem.read(appContext.getInputStream(templateFilePath));
      templateMap.put(templateFilePath,
                      templateText);
    }

    return templateText;
  }

  protected InputStream getResourcesStream(String resourceFile) {

    return appContext.getInputStream(AppProperty.RESOURCES_DIR + resourceFile);

  }
}

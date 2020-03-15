package meru.application.module.core;

import java.util.List;
import java.util.Properties;

import app.domain.Property;
import meru.app.PropertyManager;
import meru.app.engine.AppEngine;

public class PropertyManagerImpl implements PropertyManager {

  private AppEngine mAppEngine;

  public void setAppEngine(AppEngine appEngine) {
    mAppEngine = appEngine;
  }

  @Override
  public Properties getProperties() {

    Properties properties = new Properties();

    List<Property> appProperties = mAppEngine.get(mAppEngine.createQuery(Property.class));

    for (Property property : appProperties) {
      String name = property.getName();
      if (property.getPrefix() != null) {
        name = property.getPrefix() + "." + name;
      }

      String value = property.getValue();
      properties.put(name,
                     value);
    }

    return properties;
  }

}

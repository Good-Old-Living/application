package meru.application.designer.builder.resource;

import java.util.ArrayList;
import java.util.List;

import meru.application.designer.builder.BuilderConfig;
import meru.application.designer.builder.resource.db.mysql.MySQLBuilder;
import meru.application.designer.builder.resource.java.JavaClassBuilder;
import meru.application.designer.builder.resource.java.JavaEnumBuilder;
import meru.application.designer.builder.resource.java.ResourceClassProviderBuilder;
import meru.application.designer.builder.resource.java.jpa.JPADecorator;
import meru.application.designer.builder.resource.java.jpa.PersistenceXMLBuilder;
import meru.application.designer.domain.model.Entity;

public class ResourceBuilderManager {

  private List<ResourceBuilder> mResourceBuilders = new ArrayList<ResourceBuilder>(6);
  private BuilderConfig mBuilderConfig;


  public ResourceBuilderManager(BuilderConfig builderConfig) {

    
    mBuilderConfig = builderConfig;
  
    JavaClassBuilder classBuilder = new JavaClassBuilder();
    classBuilder.addDecorator(new JPADecorator());
    mResourceBuilders.add(classBuilder);

    mResourceBuilders.add(new JavaEnumBuilder());
    mResourceBuilders.add(new ResourceClassProviderBuilder());
    
    mResourceBuilders.add(new MySQLBuilder());
    mResourceBuilders.add(new PersistenceXMLBuilder());

  }


  public void init() {

    for (ResourceBuilder resourceBuilder : mResourceBuilders) {
      resourceBuilder.setContext(mBuilderConfig);
      try {
        resourceBuilder.init();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }


  public void build(Entity entity) {

    for (ResourceBuilder resourceBuilder : mResourceBuilders) {
      try {
        resourceBuilder.build(entity);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }


  public void finish() {

    for (ResourceBuilder resourceBuilder : mResourceBuilders) {
      try {
        resourceBuilder.finish();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  
  public void onError(Exception ex) {

    for (ResourceBuilder resourceBuilder : mResourceBuilders) {
      try {
        resourceBuilder.onError(ex);
      } catch (Exception e) {
       // throw new RuntimeException(e);
      }
    }
  }


  public List<ResourceBuilder> getResourceBuilders() {

    return mResourceBuilders;
  }
}

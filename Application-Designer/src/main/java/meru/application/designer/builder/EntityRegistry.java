package meru.application.designer.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meru.application.designer.domain.model.Entity;
import meru.application.designer.domain.model.EntityInterest;

public class EntityRegistry {

  private Map<String, Map<String, Entity>> mEntityMap;
  private Map<String, Map<String, List<ObserverEntity>>> mObserverMap;


  public EntityRegistry() {

    mEntityMap = new HashMap<String, Map<String, Entity>>();
    mObserverMap = new HashMap<String, Map<String, List<ObserverEntity>>>();
  }

  
  public void addEntity(Entity entity) {

    Map<String, Entity> entityMap = getEntityMap(entity.getPackage());
    entityMap.put(entity.getName(), entity);
    notifyObservers(entity);
  }


  public Entity getEntity(String packageName, String name) {

    Map<String, Entity> entityMap = getEntityMap(packageName);
    return (Entity) entityMap.get(name);
  }


  public boolean containsEntity(Entity entity) {

    Map<String, Entity> entityMap = getEntityMap(entity.getPackage());
    return entityMap.get(entity.getName()) != null;
  }


  private Map<String, Entity> getEntityMap(String packageName) {

    if (packageName == null) {
      packageName = "";
    }

    Map<String, Entity> entityMap = (Map<String, Entity>) mEntityMap.get(packageName);

    if (entityMap == null) {
      entityMap = new HashMap<String, Entity>();
      mEntityMap.put(packageName, entityMap);
    }

    return entityMap;
  }


  public void addObserver(EntityInterest interest, Entity entity) {

    List<ObserverEntity> entityList = (List<ObserverEntity>) getObserverMap(interest.getPackage()).get(interest.getEntityName());

    if (entityList == null) {
      entityList = new ArrayList<ObserverEntity>();
      getObserverMap(interest.getPackage()).put(interest.getEntityName(),
                                                entityList);
    }

    entityList.add(new ObserverEntity(entity, interest));
  }


  private void notifyObservers(Entity entity) {

    List<ObserverEntity> entityList = (List<ObserverEntity>) getObserverMap(entity.getPackage()).get(entity.getName());

    if (entityList != null)
      for (ObserverEntity observerEntity : entityList)
        observerEntity.observe(entity);
  }


  private Map<String, List<ObserverEntity>> getObserverMap(String packageName) {

    if (packageName == null) {
      packageName = "";
    }

    Map<String, List<ObserverEntity>> entityMap = (Map<String, List<ObserverEntity>>) mObserverMap.get(packageName);

    if (entityMap == null) {
      entityMap = new HashMap<String, List<ObserverEntity>>();
      mObserverMap.put(packageName, entityMap);
    }

    return entityMap;
  }

  class ObserverEntity {
    private Entity mEntity;
    private EntityInterest mEntityInterest;


    public ObserverEntity(Entity entity, EntityInterest entityInterest) {

      mEntity = entity;
      mEntityInterest = entityInterest;
    }


    public Entity getEntity() {

      return mEntity;
    }


    public EntityInterest getEntityInterest() {

      return mEntityInterest;
    }


    public void observe(Entity observableEntity) {

      mEntity.observe(mEntityInterest, observableEntity);
    }
  }
}
package meru.erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.erp.ActionEntity.EntityAction;
import meru.erp.mdm.catalog.action.VeggiesCountReportAction;
import meru.erp.mdm.catalog.action.VeggiesPriceListAction;
import meru.exception.AppException;
import meru.persistence.EntityQuery;

public class ActionEntityLifeCycle extends AbstractEntityLifeCycle<ActionEntity> {

  private Map<String, EntityAction> actionMap = new HashMap<>(1);

  @Override
  public void init() {
    super.init();
    EntityAction entityAction = new VeggiesCountReportAction();
    entityAction.setAppEngine(appEngine, appContext);
    actionMap.put(VeggiesCountReportAction.class.getSimpleName(),
                  entityAction);
    
    entityAction = new VeggiesPriceListAction();
    entityAction.setAppEngine(appEngine, appContext);
    actionMap.put(VeggiesPriceListAction.class.getSimpleName(),
                  entityAction);
  }

  private EntityAction getEntityAction(String actionName) {

    EntityAction entityAction = actionMap.get(actionName);
    if (entityAction == null) {
      throw new AppException("Unknown Action : " + actionName);
    }

    return entityAction;

  }

  @Override
  public ActionEntity preGetFirst(EntityQuery<ActionEntity> resourceFilter) {

    String action = (String) resourceFilter.getQueryParameterValue("action");
    String data = (String) resourceFilter.getQueryParameterValue("data");
    ActionEntity actionEntity = new ActionEntity(action, data);
    actionEntity = getEntityAction(action).act(actionEntity);
    return actionEntity;
  }

  @Override
  public List<ActionEntity> preGet(EntityQuery<ActionEntity> resourceFilter) {

    ActionEntity actionEntity = preGetFirst(resourceFilter);
    List<ActionEntity> actionEntityList = new ArrayList<>(1);
    actionEntityList.add(actionEntity);
    return actionEntityList;
  }

  @Override
  public boolean preCreate(ActionEntity actionEntity) {

    String action = actionEntity.getAction();

    EntityAction entityAction = actionMap.get(action);
    entityAction.act(actionEntity);

    return false;
  }

}

package meru.erp;

import meru.app.AppContext;
import meru.app.engine.AppEngine;

public class ActionEntity {

  private String action;
  private String data;

  public ActionEntity() {

  }

  public ActionEntity(String action, String data) {
    this.action = action;
    this.data = data;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public static abstract class EntityAction {
    protected AppEngine appEngine;
    protected AppContext appContext;

    public void setAppEngine(AppEngine appEngine, AppContext appContext) {
      this.appEngine = appEngine;
      this.appContext = appContext;
    }

    public ActionEntity createActionEntity() {
      ActionEntity actionEntity = new ActionEntity();
      actionEntity.action = getActionName();
      return actionEntity;
    }

    public String getActionName() {
      return getClass().getSimpleName();
    }

    public abstract ActionEntity act(ActionEntity actionEntity);

  }
}

package meru.erp.subs.lifecycle;

import app.erp.subs.Subscription;
import meru.app.AppRequest;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;

public class SubscriptionLifeCycle
    extends BusinessAppEntityLifeCycle<Subscription> {

  private static final String STATE_ENTITY_NAME = "Subscription";

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(Subscription subscription) {
    subscription.setState(getAppEntityState(STATE_ENTITY_NAME,
                                            1));
    return true;
  }

  @Override
  public Subscription postGet(Subscription subscription) {

    if (AppRequest.currentRequest().existsParameter("cancel")) {

      cancelSubscription(subscription);

    }

    return subscription;
  }

  public void cancelSubscription(Subscription subscription) {

    subscription.setState(getAppEntityState(STATE_ENTITY_NAME,
                                            2));
    appEngine.save(subscription);

  }

}

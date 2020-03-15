package meru.erp.subs.lifecycle;

import app.erp.subs.SubscriptionLineItem;
import meru.app.AppRequest;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;

public class SubscriptionLineItemLifeCycle
    extends BusinessAppEntityLifeCycle<SubscriptionLineItem> {

  private static final String STATE_ENTITY_NAME = "Subscription";

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(SubscriptionLineItem subscriptionLineItem) {
    subscriptionLineItem.setState(getAppEntityState(STATE_ENTITY_NAME,
                                            1));
    return true;
  }

  @Override
  public SubscriptionLineItem postGet(SubscriptionLineItem subscriptionLineItem) {

    if (AppRequest.currentRequest().existsParameter("cancel")) {

      cancelSubscription(subscriptionLineItem);

    }
    else if (AppRequest.currentRequest().existsParameter("resubscribe")) {

      resubscribe(subscriptionLineItem);

    }

    return subscriptionLineItem;
  }

  public void cancelSubscription(SubscriptionLineItem subscriptionLineItem) {

    subscriptionLineItem.setState(getAppEntityState(STATE_ENTITY_NAME,
                                            2));
    appEngine.save(subscriptionLineItem);

  }

  public void resubscribe(SubscriptionLineItem subscriptionLineItem) {

    subscriptionLineItem.setState(getAppEntityState(STATE_ENTITY_NAME,
                                            1));
    appEngine.save(subscriptionLineItem);

  }

}

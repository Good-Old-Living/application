package meru.erp.mdm.bp.lifecycle;

import app.erp.mdm.bp.Customer;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.app.session.Session;
import meru.app.session.SessionListener;
import meru.exception.AppException;
import meru.persistence.EntityQuery;

public class CustomerLifeCycle extends AbstractEntityLifeCycle<Customer> implements SessionListener {

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(Customer businessPartner) {

    return true;
  }

  
  
  @Override
  public Object postCreate(Customer customer) {
    
    return null;

  }

  @Override
  public void sessionInitialized(Session session) {

  }

  @Override
  public void userLoggedIn(Session session) {
    Long userId = Long.valueOf(session.getUserId());
    EntityQuery<Customer> entityQuery = appEngine.createQuery(Customer.class);
    entityQuery.addQueryParameter("userId",
                                  userId);
    Customer customer = appEngine.getFirst(entityQuery);
    if (customer == null) {
      throw new AppException("Unable to find Customer for the user : " + userId);
    }

    session.setUserId(String.valueOf(customer.getId()));
  }

  @Override
  public void userLoggedOut(Session session) {

  }

}

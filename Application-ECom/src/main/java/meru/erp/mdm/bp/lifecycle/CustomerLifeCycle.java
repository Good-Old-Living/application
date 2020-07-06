package meru.erp.mdm.bp.lifecycle;

import app.erp.mdm.bp.Customer;
import app.erp.mdm.bp.CustomerWallet;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.app.session.Session;
import meru.app.session.SessionListener;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;

public class CustomerLifeCycle extends AbstractEntityLifeCycle<Customer> implements SessionListener {

  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(Customer businessPartner) {

    return true;
  }

  @Override
  public Object postCreate(Customer customer) {

    CustomerWallet wallet = new CustomerWallet();
    wallet.setAmount(0);
    wallet.setCustomerId(customer.getId());
    wallet.setCreatedOn(mSystemCalendar.getCalendar());
    appEngine.save(wallet);

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

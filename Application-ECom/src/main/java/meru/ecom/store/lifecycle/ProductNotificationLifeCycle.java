package meru.ecom.store.lifecycle;

import app.ecom.shopping.ProductNotification;
import app.erp.mdm.bp.Customer;
import meru.app.AppRequest;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.exception.AppException;
import meru.persistence.EntityQuery;

public class ProductNotificationLifeCycle extends AbstractEntityLifeCycle<ProductNotification> {

  @Override
  public boolean preCreate(ProductNotification notification) {

    Long userId = AppRequest.currentRequest().getLoggedInUserId();
    EntityQuery<Customer> entityQuery = appEngine.createQuery(Customer.class);
    entityQuery.addQueryParameter("userId",
                                  userId);
    Customer customer = appEngine.getFirst(entityQuery);
    if (customer == null) {
      throw new AppException("Unable to find Customer for the user : " + userId);
    }

    EntityQuery<ProductNotification> ntfnQuery = appEngine.createQuery(ProductNotification.class);
    entityQuery.addQueryParameter("customer.id",
                                  customer.getId());
    entityQuery.addQueryParameter("productLineItem.id",
                                  notification.getProductLineItem().getId());

    ProductNotification productNtfn = appEngine.getFirst(ntfnQuery);
    if (productNtfn != null) {
      throw new AppException("This product is already in your notification list");
    }

    notification.setCustomer(customer);

    return true;
  }

}

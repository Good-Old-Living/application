package meru.erp.sales.lifecycle;

import app.domain.AppEntityState;
import app.domain.PropertyGroup;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderPayment;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.sales.SalesOrderState;
import meru.exception.AppException;
import meru.persistence.EntityQuery;

public class SalesOrderPaymentLifeCycle extends BusinessAppEntityLifeCycle<SalesOrderPayment> {

  @Override
  public boolean preCreate(SalesOrderPayment payment) {

    Long salesOrderId = payment.getSalesOrderId();
    SalesOrder salesOrder = null;
    if (salesOrderId != null) {
      salesOrder = appEngine.getEntity(SalesOrder.class,
                                       salesOrderId);
    }

    else {
      EntityQuery<SalesOrder> query = appEngine.createQuery(SalesOrder.class);
      String customerId = String.valueOf(payment.getCustomerId());
      query.addQueryParameter("customerId",
                              customerId);
      query.addQueryParameter("state.code",
                              SalesOrderState.PendingPayment.getCode());
      query.addQueryParameter("paymentOrderId",
                              payment.getPaymentOrderId());

      salesOrder = appEngine.getFirst(query);

      if (salesOrder == null) {
        throw new AppException("No order found with order id : " + payment.getPaymentOrderId());
      }
    }

    AppEntityState entityState = getAppEntityState(SalesOrder.class.getSimpleName(),
                                                   SalesOrderState.New.getCode());

    salesOrder.setState(entityState);
    salesOrder.setPaymentId(payment.getPaymentId());
    salesOrder.setPaymentReceivedBoolean(true);

    if (payment.getPaymentModeId() != null) {
      PropertyGroup propertyGroup = appEngine.getEntity(PropertyGroup.class,
                                                        payment.getPaymentModeId());
      salesOrder.setPaymentMode(propertyGroup);
    }

    return false;
  }

  @Override
  public boolean preModify(SalesOrderPayment payment) {

    throw new AppException("Modifications are not allowed in this entity");

  }

}

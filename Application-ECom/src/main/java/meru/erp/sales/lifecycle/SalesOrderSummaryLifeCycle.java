package meru.erp.sales.lifecycle;

import java.util.List;

import app.erp.sales.PaymentTransaction;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.sales.SalesOrderSummary;
import meru.persistence.EntityQuery;

public class SalesOrderSummaryLifeCycle extends BusinessAppEntityLifeCycle<SalesOrderSummary> {

  @Override
  public void init() {

  }

  @Override
  public SalesOrderSummary preGet(Class<SalesOrderSummary> entityClass,
                                  Object id) {

    SalesOrderSummary salesOrderSummary = new SalesOrderSummary();

    EntityQuery<SalesOrderLineItem> entityQuery = appEngine.createQuery(SalesOrderLineItem.class);
    entityQuery.addQueryParameter("salesOrderId",
                                  id);

    List<SalesOrderLineItem> lineItems = appEngine.get(entityQuery);
    for (SalesOrderLineItem lineItem : lineItems) {

      salesOrderSummary.addLineItem(lineItem);

    }
    
    SalesOrder salesOrder = appEngine.getEntity(SalesOrder.class, id);
    PaymentTransaction paymentTransaction = salesOrder.getPaymentTransaction();
    if (paymentTransaction != null && paymentTransaction.getWalletAmountDeducted() != null) {
       salesOrderSummary.reduceWalletAmount(paymentTransaction.getWalletAmountDeducted());
    }
    
    salesOrderSummary.compute();

    return salesOrderSummary;
  }

}

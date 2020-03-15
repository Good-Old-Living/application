package meru.erp.sales.lifecycle;

import java.util.List;

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
    
    salesOrderSummary.compute();
    
    return salesOrderSummary;
  }

}

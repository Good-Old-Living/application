package meru.erp.sales;

import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;


public interface SalesOrderBagProvider {

  SalesOrderBag<SalesOrderLineItem> getSalesOrderBag(SalesOrder salesOrder);
  void clearSalesOrderBag();
}

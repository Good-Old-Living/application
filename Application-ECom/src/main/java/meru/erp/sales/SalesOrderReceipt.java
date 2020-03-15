package meru.erp.sales;

import java.io.File;
import java.io.IOException;

import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;

public interface SalesOrderReceipt {

  public void store(SalesOrder salesOrder,
                    SalesOrderBag<SalesOrderLineItem> salesOrderBag,
                    File file) throws IOException;

  
}

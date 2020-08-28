package meru.ecom;

import app.erp.sales.ProductSalesReport;
import app.erp.sales.RazorPayTransaction;
import app.erp.sales.SalesOrderPayment;
import meru.application.LogMessage;
import meru.ecom.store.SessionShoppingCart;
import meru.erp.ActionEntity;
import meru.erp.sales.SalesOrderSummary;

public class EComEntityClassRegistry extends app.Application_EComEntityClassRegistry {
  protected void populateClassMap() {
    super.populateClassMap();
    addResourceClass("SessionShoppingCart",
                     SessionShoppingCart.class);
    addResourceClass("SalesOrderSummary",
                     SalesOrderSummary.class);
    addResourceClass("ProductSalesReport",
                     ProductSalesReport.class);
    addResourceClass("ActionEntity",
                     ActionEntity.class);
    addResourceClass("SalesOrderPayment",
                     SalesOrderPayment.class);
    addResourceClass("RazorPayTransaction",
                     RazorPayTransaction.class);
    addResourceClass("LogMessage",
                     LogMessage.class);
  }
}

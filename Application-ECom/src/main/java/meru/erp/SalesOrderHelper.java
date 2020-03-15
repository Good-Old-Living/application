package meru.erp;

import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductLineItem;

public class SalesOrderHelper {

  public static boolean isOrderable(ProductLineItem productLineItem) {
    Product product = productLineItem.getProduct();
    return product.orderableBoolean() && product.getProductCategory().orderableBoolean();

  }

}

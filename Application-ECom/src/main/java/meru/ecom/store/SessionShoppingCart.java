package meru.ecom.store;

import app.ecom.shopping.cart.ShoppingCartLineItem;
import app.erp.marketing.SalesOffer;
import meru.erp.sales.SalesOrderBag;


public class SessionShoppingCart extends SalesOrderBag<ShoppingCartLineItem> {

  public SessionShoppingCart() {
    this(null);
  }

  public SessionShoppingCart(SalesOffer salesOffer) {
    super(salesOffer);
  }

}

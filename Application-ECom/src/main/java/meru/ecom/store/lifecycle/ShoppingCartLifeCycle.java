package meru.ecom.store.lifecycle;

import java.util.List;

import app.ecom.shopping.cart.ShoppingCart;
import app.ecom.shopping.cart.ShoppingCartLineItem;
import app.erp.finance.Tax;
import app.erp.mdm.bp.Customer;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.app.AppRequest;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.ecom.store.SessionShoppingCart;
import meru.erp.SalesOrderHelper;
import meru.erp.mdm.catalog.lifecycle.ProductLineItemLifeCycle;
import meru.erp.sales.SalesOrderBag;
import meru.erp.sales.SalesOrderBagProvider;
import meru.erp.sales.lifecycle.SalesOrderLifeCycle;
import meru.exception.AppEntityWarning;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;

public class ShoppingCartLifeCycle extends AbstractEntityLifeCycle<ShoppingCart> implements SalesOrderBagProvider {

  private ProductLineItemLifeCycle mProductLineItemLifeCycle;

  @Override
  public void init() {
    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);
    SalesOrderLifeCycle salesOrderLifeCycle = appEngine.getEntityLifeCycle(SalesOrder.class,
                                                                           SalesOrderLifeCycle.class);
    salesOrderLifeCycle.setSalesOrderBagProvider(this);
  }

  public ShoppingCart getCurrentCart() {

    String sessionId = getSessionId();

    EntityQuery<ShoppingCart> entityQuery = appEngine.createQuery(ShoppingCart.class);
    entityQuery.addQueryParameter("sessionId",
                                  sessionId);
    ShoppingCart shoppingCart = appEngine.getFirst(entityQuery);
    if (shoppingCart == null) {

      Long userId = getLoggedInUserId();
      if (userId != null) {

        EntityQuery<Customer> customerQuery = appEngine.createQuery(Customer.class);
        customerQuery.addQueryParameter("userId",
                                        userId);
        Customer customer = appEngine.getFirst(customerQuery);

        if (customer != null) {

          EntityQuery<ShoppingCart> shoppingCartQuery = appEngine.createQuery(ShoppingCart.class);
          shoppingCartQuery.addQueryParameter("customer.id",
                                              customer.getId());
          // To avoid stackoverflow because of preGet.
          ShoppingCart prevCart = appEngine.getFirst(shoppingCartQuery);
          if (prevCart != null) {
            prevCart.setSessionId(sessionId);
            appEngine.save(prevCart);
            return prevCart;
          }
        }
      }

    } else {

      Customer customer = shoppingCart.getCustomer();
      if (customer == null) {

        Long userId = getLoggedInUserId();
        if (userId != null) {
          return setCustomer(shoppingCart,
                             userId);
          // appEngine.save(shoppingCart);
        }

      }
    }

    return shoppingCart;

  }

  public ShoppingCart createShoppingCart() {
    String sessionId = getSessionId();
    Long userId = getLoggedInUserId();

    ShoppingCart shoppingCart = null;
    Customer customer = null;
    if (userId != null) {

      customer = getCustomer(userId);
      if (customer != null) {
        shoppingCart = getShoppingCart(customer);
      }
    }

    if (shoppingCart == null) {

      shoppingCart = new ShoppingCart();
      shoppingCart.setCreatedTime(SystemCalendar.getInstance().getUTCCalendar());
      shoppingCart.setSessionId(sessionId);
      shoppingCart.setCustomer(customer);
      appEngine.save(shoppingCart);
    }

    return shoppingCart;

  }

  public SessionShoppingCart getCurrentSessionShoppingCart() {

    SessionShoppingCart shoppingBag = new SessionShoppingCart();
    List<ShoppingCartLineItem> lineItems = getCurrentShoppingCartLineItems();
    if (lineItems != null && !lineItems.isEmpty()) {
      for (ShoppingCartLineItem lineItem : lineItems) {
        ProductLineItem productLineItem = lineItem.getProductLineItem();
        shoppingBag.addLineItem(productLineItem,
                                lineItem.getQuantity(),
                                mProductLineItemLifeCycle.getTax(productLineItem));
      }

      shoppingBag.compute();
    }

    return shoppingBag;
  }

  public SalesOrderBag<SalesOrderLineItem> getCurrentSalesOrderBag(SalesOrder salesOrder) {

    SalesOrderBag<SalesOrderLineItem> salesOrderBag = new SalesOrderBag<SalesOrderLineItem>();
    List<ShoppingCartLineItem> lineItems = getCurrentShoppingCartLineItems();
    if (lineItems != null && !lineItems.isEmpty()) {
      for (ShoppingCartLineItem lineItem : lineItems) {
        ProductLineItem productLineItem = lineItem.getProductLineItem();
        
//        if (!SalesOrderHelper.isOrderable(productLineItem)) {
//          
//        }
        
        
        SalesOrderLineItem salesOrderLineItem = new SalesOrderLineItem();
        salesOrderLineItem.setSalesOrderId(salesOrder.getId());

        salesOrderLineItem.setProductLineItem(productLineItem);
        salesOrderLineItem.setQuantity(lineItem.getQuantity());
        salesOrderLineItem.setUnitMrp(productLineItem.getMrp());
        salesOrderLineItem.setUnitPrice(productLineItem.getPrice());
        salesOrderLineItem.setDiscount(productLineItem.getDiscount());
        salesOrderLineItem.setDiscountType(productLineItem.getDiscountType());

        float taxRate = 0F;
        Tax tax = mProductLineItemLifeCycle.getTax(productLineItem);
        if (tax != null) {
          taxRate = tax.getRate();
        }
        salesOrderLineItem.setTaxRate(taxRate);

        salesOrderBag.addLineItem(salesOrderLineItem);
      }

      salesOrderBag.compute();
    }

    return salesOrderBag;
  }

  public List<ShoppingCartLineItem> getCurrentShoppingCartLineItems() {

    EntityQuery<ShoppingCartLineItem> entityQuery = appEngine.createQuery(ShoppingCartLineItem.class);
    List<ShoppingCartLineItem> cartItems = appEngine.get(entityQuery);
    return cartItems;
  }

  @Override
  public ShoppingCart preGet(Class<ShoppingCart> entityClass,
                             Object id) {

    if (AppRequest.currentRequest().existsParameter("checkout")) {

    } else if (AppRequest.currentRequest().existsParameter("clear")) {
      clearCurrentCartItems();
    }

    return null;
  }

  public void clearCurrentCart() {

    ShoppingCart shoppingCart = getCurrentCart();
    if (shoppingCart != null) {
      deleteShoppingCartLineItems(shoppingCart.getId());
      appEngine.remove(ShoppingCart.class,
                       shoppingCart.getId());
    }
  }

  private void clearCurrentCartItems() {

    ShoppingCart shoppingCart = getCurrentCart();
    if (shoppingCart != null) {
      deleteShoppingCartLineItems(shoppingCart.getId());
    }
  }

  private void deleteShoppingCartLineItems(Long shoppingCartId) {

    EntityQuery<ShoppingCartLineItem> entityQuery = appEngine.createQuery(ShoppingCartLineItem.class);
    entityQuery.addQueryParameter("shoppingCartId",
                                  shoppingCartId);
    List<ShoppingCartLineItem> cartItems = appEngine.get(entityQuery);
    for (ShoppingCartLineItem cartItem : cartItems) {
      appEngine.remove(ShoppingCartLineItem.class,
                       cartItem.getId());
    }

  }

  @Override
  public SalesOrderBag<SalesOrderLineItem> getSalesOrderBag(SalesOrder salesOrder) {

    return getCurrentSalesOrderBag(salesOrder);
  }

  @Override
  public void clearSalesOrderBag() {
    clearCurrentCartItems();
  }

  private Customer getCustomer(Long userId) {
    EntityQuery<Customer> customerQuery = appEngine.createQuery(Customer.class);
    customerQuery.addQueryParameter("userId",
                                    userId);
    Customer customer = appEngine.getFirst(customerQuery);

    return customer;
  }

  private ShoppingCart getShoppingCart(Customer customer) {
    EntityQuery<ShoppingCart> shoppingCartQuery = appEngine.createQuery(ShoppingCart.class);
    shoppingCartQuery.addQueryParameter("customer.id",
                                        customer.getId());
    ShoppingCart shoppingCart = appEngine.getFirst(shoppingCartQuery);

    return shoppingCart;
  }

  private ShoppingCart setCustomer(ShoppingCart shoppingCart,
                                   Long userId) {

    EntityQuery<Customer> customerQuery = appEngine.createQuery(Customer.class);
    customerQuery.addQueryParameter("userId",
                                    userId);
    Customer customer = appEngine.getFirst(customerQuery);

    EntityQuery<ShoppingCart> shoppingCartQuery = appEngine.createQuery(ShoppingCart.class);
    shoppingCartQuery.addQueryParameter("customer.id",
                                        customer.getId());
    // To avoid stackoverflow because of preGet.
    ShoppingCart prevCart = appEngine.getFirst(shoppingCartQuery);
    if (prevCart != null) {

      EntityQuery<ShoppingCartLineItem> scLineItemQuery = appEngine.createQuery(ShoppingCartLineItem.class);
      scLineItemQuery.addQueryParameter("shoppingCartId",
                                        shoppingCart.getId());
      List<ShoppingCartLineItem> prevCartLineItems = appEngine.get(scLineItemQuery);

      if (prevCartLineItems != null) {

        for (ShoppingCartLineItem lineItem : prevCartLineItems) {

          EntityQuery<ShoppingCartLineItem> lineItemQuery = appEngine.createQuery(ShoppingCartLineItem.class);
          lineItemQuery.addQueryParameter("shoppingCartId",
                                          prevCart.getId());
          lineItemQuery.addQueryParameter("productLineItem.id",
                                          lineItem.getId());
          // To avoid stackoverflow because of preGet.
          ShoppingCartLineItem currLineItem = appEngine.getFirst(lineItemQuery);
          if (currLineItem == null) {

            ShoppingCartLineItem newLineItem = new ShoppingCartLineItem();
            newLineItem.setProductLineItem(lineItem.getProductLineItem());
            newLineItem.setQuantity(lineItem.getQuantity());
            newLineItem.setTotalPrice(lineItem.getTotalPrice());
            newLineItem.setShoppingCartId(prevCart.getId());
            try {
              appEngine.saveEntity(newLineItem);
            } catch (AppEntityWarning e) {
              // TODO just ignore for now.
            }
          }

        }
      }

      // prevCart.setCustomer(null);
      prevCart.setSessionId(shoppingCart.getSessionId());
      appEngine.save(prevCart);

      try {
        appEngine.remove(ShoppingCart.class,
                         shoppingCart.getId());
      } catch (Exception e) {
        // Ignore if not found
      }
      return prevCart;

    }

    shoppingCart.setCustomer(customer);
    appEngine.save(shoppingCart);
    return shoppingCart;

  }
}

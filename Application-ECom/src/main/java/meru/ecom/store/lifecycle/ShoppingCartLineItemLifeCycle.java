package meru.ecom.store.lifecycle;

import java.util.ArrayList;
import java.util.List;

import app.ecom.shopping.cart.ShoppingCart;
import app.ecom.shopping.cart.ShoppingCartLineItem;
import app.erp.mdm.catalog.ProductLineItem;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.ecom.store.SessionShoppingCart;
import meru.erp.mdm.catalog.lifecycle.ProductLineItemLifeCycle;
import meru.exception.AppEntityWarning;
import meru.persistence.EntityQuery;

public class ShoppingCartLineItemLifeCycle extends AbstractEntityLifeCycle<ShoppingCartLineItem> {

  private ShoppingCartLifeCycle mShoppingCartLifeCycle;
  private ProductLineItemLifeCycle mProductLineItemLifeCycle;

  @Override
  public void init() {
    mShoppingCartLifeCycle = appEngine.getEntityLifeCycle(ShoppingCart.class,
                                                          ShoppingCartLifeCycle.class);
    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);
  }

  @Override
  public boolean preCreate(ShoppingCartLineItem cartItem) {

    // ShoppingCart shoppingCart1 = mShoppingCartLifeCycle.getCurrentCart();
    Long shoppingCartId = cartItem.getShoppingCartId();

    if (shoppingCartId == null) {
      ShoppingCart shoppingCart = mShoppingCartLifeCycle.getCurrentCart();
      if (shoppingCart == null) {
        shoppingCart = mShoppingCartLifeCycle.createShoppingCart();
      }

      shoppingCartId = shoppingCart.getId();
    }

    EntityQuery<ShoppingCartLineItem> entityQuery = appEngine.createQuery(ShoppingCartLineItem.class);
    entityQuery.addQueryParameter("shoppingCartId",
                                  shoppingCartId);

    entityQuery.addQueryParameter("productLineItem.id",
                                  cartItem.getProductLineItem().getId());

    ShoppingCartLineItem prevItem = appEngine.getFirst(entityQuery);

    if (prevItem == null) {

      if (cartItem.getQuantity() != 0) {

        ProductLineItem productItem = appEngine.get(ProductLineItem.class,
                                                    cartItem.getProductLineItem().getId());
        mProductLineItemLifeCycle.checkAvailableQuantity(productItem,
                                                         cartItem.getQuantity());
        cartItem.setProductLineItem(productItem);
        cartItem.setShoppingCartId(shoppingCartId);
        setTotalPrice(cartItem);
      } else {
        throw new AppEntityWarning("NotAdded", cartItem);
      }

    } else {

      String code = null;
      Object result = null;
      int currQty = cartItem.getQuantity();
      if (currQty == 0) {

        result = appEngine.remove(ShoppingCartLineItem.class,
                                  prevItem.getId());
        code = "Deleted";

      } else if (currQty != prevItem.getQuantity()) {

        mProductLineItemLifeCycle.checkAvailableQuantity(prevItem.getProductLineItem(),
                                                         cartItem.getQuantity());
        prevItem.setQuantity(cartItem.getQuantity());
        setTotalPrice(prevItem);
        result = appEngine.save(prevItem);
        code = "Modified";
      } else {
        code = "NotModified";
      }

      throw new AppEntityWarning(code, result);
    }

    return true;
  }

  @Override
  public boolean preModify(ShoppingCartLineItem cartItem) {
    ShoppingCartLineItem cartLineItem = appEngine.get(ShoppingCartLineItem.class,
                                                      cartItem.getId());
    if (cartLineItem == null) {
      return preCreate(cartItem);
    }

    setTotalPrice(cartLineItem);

    return true;
  }

  @Override
  public Object postCreate(ShoppingCartLineItem cartItem) {
    return postCreateOrModify(cartItem);
  }

  @Override
  public Object postModify(ShoppingCartLineItem cartItem) {
    return postCreateOrModify(cartItem);
  }

  @Override
  public Object postDelete(ShoppingCartLineItem cartItem) {
    return postCreateOrModify(cartItem);
  }

  @Override
  public List<ShoppingCartLineItem> preGet(EntityQuery<ShoppingCartLineItem> entityQuery) {

    if (entityQuery.hasQueryParameter("shoppingCartId")) {
      return null;
    }

    ShoppingCart shoppingCart = mShoppingCartLifeCycle.getCurrentCart();
    if (shoppingCart != null) {

      entityQuery.addQueryParameter("shoppingCartId",
                                    shoppingCart.getId());
    } else {
      return new ArrayList<ShoppingCartLineItem>(0);
    }

    return null;
  }

  private Object postCreateOrModify(ShoppingCartLineItem cartItem) {

    SessionShoppingCart shoppingBag = mShoppingCartLifeCycle.getCurrentSessionShoppingCart();

    // String message = mShoppingAdvisor.advice(shoppingBag);
    //
    // if (message != null) {
    // result.setMessage(message);
    // }

    return shoppingBag;
    // return null;
  }

  private static void setTotalPrice(ShoppingCartLineItem cartItem) {
    cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProductLineItem().getPrice());
  }
}

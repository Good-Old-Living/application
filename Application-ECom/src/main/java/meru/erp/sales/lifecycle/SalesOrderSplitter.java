package meru.erp.sales.lifecycle;

import java.util.List;

import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.app.engine.AppEngine;
import meru.erp.mdm.catalog.FruitVegKey;
import meru.exception.AppException;
import meru.persistence.EntityQuery;

public class SalesOrderSplitter {

  private AppEngine appEngine;

  public SalesOrderSplitter(AppEngine appEngine) {
    this.appEngine = appEngine;
  }

  public SalesOrder splitVegetableItems(SalesOrder salesOrder) {

    EntityQuery<SalesOrderLineItem> soLineItemQuery = appEngine.createQuery(SalesOrderLineItem.class);
    soLineItemQuery.addQueryParameter("salesOrderId",
                                      salesOrder.getId());

    List<SalesOrderLineItem> existingLineItems = appEngine.get(soLineItemQuery);

    SalesOrder newSalesOrder = null;
    float amount = 0;

    for (SalesOrderLineItem soLineItem : existingLineItems) {

      if (!FruitVegKey.containsKey(soLineItem.getProductLineItem().getProduct().getProductCategory().getId())) {

        if (newSalesOrder == null) {
          newSalesOrder = createSplitOrderForNonVeggies(salesOrder);
        }

        SalesOrderLineItem newSOLineItem = createNonVeggiesLineItem(soLineItem,
                                                                    newSalesOrder.getId());
        
        amount += newSOLineItem.getTotalPrice();
      }

    }

    
    if (newSalesOrder == null) {
      throw new AppException("Nothing to split");
    }
    
    newSalesOrder.setAmount(amount);
    appEngine.save(newSalesOrder);

    return newSalesOrder;

  }

  private SalesOrder createSplitOrderForNonVeggies(SalesOrder salesOrder) {

    SalesOrder newSalesOrder = new SalesOrder();
    newSalesOrder.setAmount(salesOrder.getAmount());
    newSalesOrder.setCode(salesOrder.getCode());
    newSalesOrder.setCreatedOn(salesOrder.getCreatedOn());
    newSalesOrder.setCustomerId(salesOrder.getCustomerId());
    newSalesOrder.setDeliveryAddress(salesOrder.getDeliveryAddress());
    newSalesOrder.setDeliveryAddressText(salesOrder.getDeliveryAddressText());
    newSalesOrder.setDeliveryInstructions(salesOrder.getDeliveryInstructions());
    newSalesOrder.setOrderId(salesOrder.getOrderId() + "-1");
    newSalesOrder.setPaymentMethod(salesOrder.getPaymentMethod());
    newSalesOrder.setSessionId(salesOrder.getSessionId());
    newSalesOrder.setState(salesOrder.getState());

    appEngine.save(newSalesOrder);
    return newSalesOrder;
  }

  private SalesOrderLineItem createNonVeggiesLineItem(SalesOrderLineItem soLineItem,
                                                      Long salesOrderId) {

    SalesOrderLineItem newSOLineItem = new SalesOrderLineItem();
    newSOLineItem.setDiscount(soLineItem.getDiscount());
    newSOLineItem.setDiscountType(soLineItem.getDiscountType());
    newSOLineItem.setNetQuantity(soLineItem.getNetQuantity());
    newSOLineItem.setNotes(soLineItem.getNotes());
    newSOLineItem.setProductLineItem(soLineItem.getProductLineItem());
    newSOLineItem.setQuantity(soLineItem.getQuantity());
    newSOLineItem.setSalesOrderId(salesOrderId);
    newSOLineItem.setTaxRate(soLineItem.getTaxRate());
    newSOLineItem.setTotalPrice(soLineItem.getTotalPrice());
    newSOLineItem.setUnitMrp(soLineItem.getUnitMrp());
    newSOLineItem.setUnitPrice(soLineItem.getUnitPrice());

    appEngine.save(newSOLineItem);
    appEngine.remove(SalesOrderLineItem.class,
                     soLineItem.getId());

    return newSOLineItem;
  }
}

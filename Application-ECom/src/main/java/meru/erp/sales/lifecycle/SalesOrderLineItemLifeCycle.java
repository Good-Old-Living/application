package meru.erp.sales.lifecycle;

import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.erp.mdm.catalog.lifecycle.ProductLineItemLifeCycle;
import meru.erp.sales.SalesOrderBag;
import app.domain.PropertyGroup;
import app.erp.finance.Tax;
import app.erp.marketing.SalesOffer;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.SalesOrderLineItem;

public class SalesOrderLineItemLifeCycle extends AbstractEntityLifeCycle<SalesOrderLineItem> {

  private ProductLineItemLifeCycle mProductLineItemLifeCycle;

  @Override
  public void init() {
    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);
  }

  public void popuplateSalesOrderLineItem(SalesOrderLineItem lineItem,
                                          SalesOffer salesOffer) {

    ProductLineItem productItem = lineItem.getProductLineItem();

    Float mrp = lineItem.getUnitMrp();
    if (mrp == null || mrp == 0) {
      lineItem.setUnitMrp(productItem.getMrp());

      Tax tax = mProductLineItemLifeCycle.getTax(productItem);
      if (tax == null) {
        lineItem.setTaxRate(0F);
      } else {
        lineItem.setTaxRate(tax.getRate());
      }
    }

    Float discount = null;
    PropertyGroup discountType = null;

    if (lineItem.getDiscount() == null) {

      if (salesOffer != null && salesOffer.getDiscount() > lineItem.getDiscount()) {
        discount = salesOffer.getDiscount();
        discountType = salesOffer.getDiscountType();
      } else {
        discount = productItem.getDiscount();
        discountType = productItem.getDiscountType();
      }

      lineItem.setDiscount(discount);
      lineItem.setDiscountType(discountType);
    } else {

      discount = lineItem.getDiscount();
      discountType = lineItem.getDiscountType();
    }

    float unitPrice = SalesOrderBag.getUnitPrice(lineItem.getUnitMrp(),
                                                 discount,
                                                 discountType);

    lineItem.setUnitPrice(unitPrice);

    float totalPrice = SalesOrderBag.getTotalPrice(lineItem.getUnitMrp(),
                                                   (lineItem.getNetQuantity() == null) ? lineItem.getQuantity()
                                                       : lineItem.getNetQuantity(),
                                                   lineItem.getDiscount(),
                                                   lineItem.getDiscountType());

    lineItem.setTotalPrice(totalPrice);

    if (lineItem.getTaxRate() == null || lineItem.getTaxRate() == -1) {

      Tax tax = mProductLineItemLifeCycle.getTax(productItem);
      if (tax == null) {
        lineItem.setTaxRate(0F);
      } else {
        lineItem.setTaxRate(tax.getRate());
      }

    }
  }

  @Override
  public boolean preCreate(SalesOrderLineItem lineItem) {
    popuplateSalesOrderLineItem(lineItem,
                                null);
    return true;
  }

  @Override
  public boolean preModify(SalesOrderLineItem lineItem) {

    if (lineItem.getProductLineItem() == null) {
      SalesOrderLineItem soLineItem = appEngine.get(SalesOrderLineItem.class,
                                                    lineItem.getId());

      if (lineItem.getQuantity() == 0) {
        appEngine.remove(SalesOrderLineItem.class,
                         lineItem.getId());
        return false;
      } else {
        soLineItem.setQuantity(lineItem.getQuantity());
        copy(soLineItem,
             lineItem);
      }

    }

    popuplateSalesOrderLineItem(lineItem,
                                null);

    return true;
  }

  private static void copy(SalesOrderLineItem fromLineItem,
                           SalesOrderLineItem toLineItem) {

    toLineItem.setDiscount(fromLineItem.getDiscount());
    toLineItem.setDiscountType(fromLineItem.getDiscountType());
    toLineItem.setNetQuantity(fromLineItem.getNetQuantity());
    toLineItem.setNotes(fromLineItem.getNotes());
    toLineItem.setProductLineItem(fromLineItem.getProductLineItem());
    toLineItem.setQuantity(fromLineItem.getQuantity());

    toLineItem.setSalesOrderId(fromLineItem.getSalesOrderId());
    toLineItem.setTaxRate(fromLineItem.getTaxRate());

    toLineItem.setTotalPrice(fromLineItem.getTotalPrice());
    toLineItem.setUnitMrp(fromLineItem.getUnitMrp());
    toLineItem.setUnitPrice(fromLineItem.getUnitPrice());

  }

}

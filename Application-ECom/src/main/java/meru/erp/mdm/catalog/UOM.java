package meru.erp.mdm.catalog;

import app.erp.mdm.catalog.ProductLineItem;

public enum UOM {
                 KG {
                   public boolean isUnitPrice() {

                     return true;
                   }
                 },
                 GM {
                   public float toFloatQuantity(ProductLineItem productLineItem) {

                     float quantity = productLineItem.getQuantity();

                     return (quantity / 100) * 0.1f;

                   }

                   public float adjustPrice(ProductLineItem productLineItem,
                                            float price) {

                     float adjPrice = (productLineItem.getQuantity() / 1000f) * price;
                     adjPrice = (float) Math.ceil(adjPrice);
                     return adjPrice;
                   }

                   public boolean isUnitPrice() {

                     return false;
                   }

                 },
                 BUNCH {
                   public boolean isUnitPrice() {

                     return true;
                   }
                 },
                 PC {
                   public boolean isUnitPrice() {

                     return true;
                   }
                 };

  public float toFloatQuantity(ProductLineItem productLineItem) {

    return productLineItem.getQuantity();
  }

  public float adjustPrice(ProductLineItem productLineItem,
                           float price) {

    return price;
  }

  /**
   * Indicates if it represents Kg, Pc, Bunch, etc, which are used to measure the price of an item.
   * @param productLineItem
   * @param price
   * @return
   */

  public boolean isUnitPrice(ProductLineItem productLineItem) {

    return true;
  }

  public static boolean representsUnitPrice(ProductLineItem productLineItem) {
    String uom = productLineItem.getUnitOfMeasure().getValue();
    return getUnitOfMessure(uom).isUnitPrice(productLineItem);
  }

  public static UOM getUnitOfMessure(String uom) {

    UOM unitOfMeasure = valueOf(uom.toUpperCase());

    if (unitOfMeasure == null) {
      throw new RuntimeException("Unknow UnitOfMeasure : " + uom);
    }

    return unitOfMeasure;
  }
}

package meru.erp.sales;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meru.erp.DiscountType;
import meru.erp.EComAppProperty;
import app.domain.PropertyGroup;
import app.erp.finance.Tax;
import app.erp.marketing.SalesOffer;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.SalesOrderLineItem;

public class SalesOrderBag<T> {

  //  public static final int FREE_SHIPPING_ORDER = 500;
  //  public static final int DELIVERY_CHARGE = 20;

  private int itemCount;

  private float grandTotal;
  private float subTotal;
  private float savings;
  private float tax;
  //  private int pointsEarned;
  private int deliveryCharge;

  private List<T> lineItems;
  private Map<Float, SalesTax> taxSummary;
  private int walletAmount;

  private SalesOffer salesOffer;

  public SalesOrderBag() {
    this(null);
  }

  public SalesOrderBag(SalesOffer salesOffer) {

    lineItems = new ArrayList<T>(1);
    taxSummary = new HashMap<Float, SalesTax>(1);

    this.salesOffer = salesOffer;
  }

  public static final float getUnitPrice(float mrp,
                                         Float discount,
                                         PropertyGroup discountType) {

    float price = 0;
    if (discount == null || discount == 0F) {
      price = mrp;
    } else {
      if (discountType != null) {
        discount = mrp * DiscountType.PERCENT.getValue(discount);
      }
      price = mrp - discount;
    }

    return price;
  }

  public static final float getTotalPrice(float mrp,
                                          float quantity,
                                          Float discount,
                                          PropertyGroup discountType) {

    if (discount == null) {
      discount = 0F;
    }

    float price = getUnitPrice(mrp,
                               discount,
                               discountType);
    return price * quantity;

  }

  public static final float getSavings(float mrp,
                                       float quantity,
                                       float totalPrice) {
    return (mrp * quantity) - totalPrice;

  }

  private float addItem(float mrp,
                        float quantity,
                        Float discount,
                        PropertyGroup discountType,
                        float taxRate) {

    float totalPrice = getTotalPrice(mrp,
                                     quantity,
                                     discount,
                                     discountType);

    grandTotal += totalPrice;
    subTotal = grandTotal;

    SalesTax salesTax = taxSummary.get(taxRate);
    if (salesTax == null) {
      salesTax = new SalesTax(taxRate);
      taxSummary.put(taxRate,
                     salesTax);
    }
    salesTax.addAmount(totalPrice);

    savings += getSavings(mrp,
                          quantity,
                          totalPrice);

    itemCount++;

    return totalPrice;
  }

  public int reduceWalletAmount(int amount) {

    int amountToDeduct = 0;
    if (amount >= grandTotal) {
      amountToDeduct = Math.round(grandTotal);
      grandTotal = 0;
    } else {
      amountToDeduct = amount;
      grandTotal -= amount;
    }
    walletAmount = amountToDeduct;
    
    return amountToDeduct;
  }

  @SuppressWarnings("unchecked")
  public void addLineItem(ProductLineItem productLineItem,
                          Integer quantity,
                          Tax tax) {

    lineItems.add((T) productLineItem);

    float taxRate = 0F;
    if (tax != null) {
      taxRate = tax.getRate();
    }

    addItem(productLineItem.getMrp(),
            quantity,
            productLineItem.getDiscount(),
            productLineItem.getDiscountType(),
            taxRate);
  }

  @SuppressWarnings("unchecked")
  public void addLineItem(SalesOrderLineItem lineItem) {

    lineItems.add((T) lineItem);

    float price = addItem(lineItem.getUnitMrp(),
                          (lineItem.getNetQuantity() == null) ? lineItem.getQuantity() : lineItem.getNetQuantity(),
                          lineItem.getDiscount(),
                          lineItem.getDiscountType(),
                          lineItem.getTaxRate() == null ? 0F : lineItem.getTaxRate());

    lineItem.setTotalPrice(price);
  }

  public void compute() {

    //    for (SalesTax salesTax : taxSummary.values()) {
    //      tax += salesTax.compute();
    //    }

    subTotal = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(subTotal));

    grandTotal = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(grandTotal));
    savings = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(savings));
    tax = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(tax));

    //    pointsEarned = Math.round(grandTotal / 100);
    deliveryCharge = getDeliveryCharge(grandTotal);

  }

  public static final int getDeliveryCharge(float grandTotal) {
    //    if (grandTotal < FREE_SHIPPING_ORDER) {
    //      return DELIVERY_CHARGE;
    //    }

    return 0;
  }

  public List<T> getLineItems() {
    return lineItems;
  }

  public SalesOffer getSalesOffer() {
    return salesOffer;
  }

  public boolean isEmpty() {
    return itemCount == 0;
  }

  public int getItemCount() {
    return itemCount;
  }

  public float getGrandTotal() {
    return grandTotal;
  }

  public float getSubTotal() {
    return subTotal;
  }

  public float getSavings() {
    return savings;
  }

  public float getWalletAmount() {
    return walletAmount;
  }

  public float getTax() {
    return tax;
  }

  //  public int getPointsEarned() {
  //    return pointsEarned;
  //  }

  public int getDeliveryCharge() {
    return deliveryCharge;
  }

  public float getTotalAmount() {
    return grandTotal + deliveryCharge;
  }

  public Collection<SalesTax> getSalesTax() {
    return taxSummary.values();
  }

  public boolean containsTaxSummary() {
    return taxSummary != null;
  }
}

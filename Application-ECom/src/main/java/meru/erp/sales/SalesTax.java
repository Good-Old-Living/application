package meru.erp.sales;

import meru.erp.EComAppProperty;

public class SalesTax {

  private float taxRate;
  private float taxableAmount;
  private float tax;
  private float totalAmount;

  public SalesTax(float taxRate) {
    this.taxRate = taxRate;
  }

  public void addAmount(float amount) {
    totalAmount += amount;
  }

  public float compute() {
    tax = totalAmount * (taxRate / 100);
    taxableAmount = totalAmount - tax;

    tax = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(tax));
    taxableAmount = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(taxableAmount));
    totalAmount = Float.parseFloat(EComAppProperty.FLOAT_FORMAT.format(totalAmount));

    return tax;
  }

  public float getTaxRate() {
    return taxRate;
  }

  public float getTaxableAmount() {
    return taxableAmount;
  }

  public float getTax() {
    return tax;
  }

  public float getTotalAmount() {
    return totalAmount;
  }

}

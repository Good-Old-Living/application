package app.erp.sales;

import app.erp.mdm.catalog.Product;

public class ProductSalesReport {

  private app.erp.mdm.catalog.Product product;
  private String code;
  private java.util.Calendar fromDateTime;

  private java.util.Calendar toDateTime;

  private Number quantity;

  public ProductSalesReport(Product product, String code, Number quantity) {
    this.product = product;
    this.code = code;
    this.quantity = quantity;
  }
  
  public long getId() {
    return 0;
  }

  public app.erp.mdm.catalog.Product getProduct() {

    return product;
  }

  public String getCode() {
    return code;
  }
  
  public java.util.Calendar getFromDateTime() {

    return fromDateTime;
  }

  public java.util.Calendar getToDateTime() {

    return toDateTime;
  }

  public Number getQuantity() {

    return quantity;
  }

}

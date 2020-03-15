package meru.erp.mdm.catalog;

import meru.persistence.EntityQuery;
import meru.ui.faces.EntityDataProvider;
import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductLineItem;

public class ProductLineItemDN {

  private String categoryDN;
  private String brand;
  private String productName;
  private int quantity;
  private String unitOfMeasure;

  public ProductLineItemDN(String dn) {
    int index = dn.lastIndexOf('/');
    String[] productItem = dn.substring(index + 1)
                             .split("[ ]");
    quantity = Integer.parseInt(productItem[0]);
    unitOfMeasure = productItem[1];

    String productDN = dn.substring(0,
                                    index);
    index = productDN.lastIndexOf('/');

    categoryDN = productDN.substring(0,
                                     index);
    String product = productDN.substring(index + 1);
    index = product.indexOf('~');
    if (index > 0) {
      brand = product.substring(0,
                                index);
    }
    productName = product.substring(index + 1);
  }

  public String getCategoryDN() {
    return categoryDN;
  }

  public String getBrand() {
    return brand;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getUnitOfMeasure() {
    return unitOfMeasure;
  }

  public EntityQuery<ProductLineItem> getEntityQuery(EntityDataProvider entityDataProvider) {

    EntityQuery<ProductLineItem> entityQuery = entityDataProvider.createEntityQuery(ProductLineItem.class);
    entityQuery.addQueryParameter("product.productCategory.qualifiedName",
                                  categoryDN);

    if (brand != null) {
      entityQuery.addQueryParameter("product.brand",
                                    brand);
    }
    entityQuery.addQueryParameter("product.name",
                                  productName);
    entityQuery.addQueryParameter("quantity",
                                  quantity);
    entityQuery.addQueryParameter("unitOfMeasure.value",
                                  unitOfMeasure);

    return entityQuery;
  }

  public static String getDN(String categoryDN,
                             String brand,
                             String productName,
                             String quantity,
                             String unitOfMeasure) {
    StringBuilder strBuilder = new StringBuilder(categoryDN);
    strBuilder.append('/');
    if (brand != null) {
      strBuilder.append(brand)
                .append("~");
    }
    strBuilder.append(productName)
              .append("")
              .append(quantity)
              .append(" ")
              .append(unitOfMeasure);
    return strBuilder.toString();
  }

  public static String getDN(ProductLineItem productItem) {
    Product product = productItem.getProduct();
    StringBuilder strBuilder = new StringBuilder(product.getProductCategory()
                                                        .getQualifiedName());
    strBuilder.append('/');
    if (product.getBrand() != null) {
      strBuilder.append(product.getBrand())
                .append("~");
    }
    strBuilder.append(product.getName())
              .append(" ")
              .append(productItem.getQuantity())
              .append(" ")
              .append(productItem.getUnitOfMeasure()
                                 .getValue());
    return strBuilder.toString();
  }

  public static String getProductNameWithQuantity(ProductLineItem productItem) {
    Product product = productItem.getProduct();
    StringBuilder strBuilder = new StringBuilder();

    if (product.getBrand() != null) {
      strBuilder.append(product.getBrand())
                .append("~");
    }
    strBuilder.append(product.getName())
              .append(" ")
              .append(productItem.getQuantity())
              .append(" ")
              .append(productItem.getUnitOfMeasure()
                                 .getValue());
    return strBuilder.toString();
  }

  public static String getProductItemLabel(ProductLineItem productItem) {
    Product product = productItem.getProduct();
    StringBuilder strBuilder = new StringBuilder();

    if (product.getBrand() != null) {
      strBuilder.append(product.getBrand())
                .append(" ");
    }
    strBuilder.append(product.getName())
              .append(" ")
              .append(productItem.getQuantity())
              .append(" ")
              .append(productItem.getUnitOfMeasure()
                                 .getValue());
    return strBuilder.toString();
  }
}

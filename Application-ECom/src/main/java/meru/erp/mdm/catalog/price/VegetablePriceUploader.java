package meru.erp.mdm.catalog.price;

import java.io.IOException;

import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductLineItem;
import meru.app.engine.AppEngine;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;

public class VegetablePriceUploader {

  private AppEngine appEngine;

  public VegetablePriceUploader(AppEngine appEngine) {
    this.appEngine = appEngine;
  }

  public boolean upload(String productCode,
                        String measurement,
                        float price) throws IOException {

    boolean processed = true;
    System.out.println("Uploading product " + productCode + " : " + price);

    EntityQuery<ProductLineItem> entityQuery = appEngine.createQuery(ProductLineItem.class);
    entityQuery.addQueryParameter("code",
                                  AttributeOperator.LIKE,
                                  "SO" + productCode + "%");

    if (measurement.equalsIgnoreCase("Kg")) {
      entityQuery.addQueryParameter("quantity",
                                    1);

      entityQuery.addQueryParameter("unitOfMeasure.value",
                                    measurement.toLowerCase());
    }

    ProductLineItem productLineItem = appEngine.getFirst(entityQuery);
    if (productLineItem == null) {
      System.out.println("No product found with code  : " + productCode);
      processed = false;
    } else {

      if (price == 0) {
       enableProduct(productLineItem, false);
      }

      else {
        enableProduct(productLineItem, true);
        //productLineItem.setInStockBoolean(true);
        double profit = Math.ceil(price * 0.25f);
        if (profit > 40) {
          profit = 40;
        }

        price += profit;

        productLineItem.setMrp(price);
        productLineItem.setPrice(price);
        appEngine.save(productLineItem);
      }
    }
    
    return processed;

  }

  private void enableProduct(ProductLineItem productLineItem, boolean enable) {
    Product product = productLineItem.getProduct();
    product.setIsActiveBoolean(enable);
    appEngine.save(product);
//    productLineItem.setInStockBoolean(false);
//    appEngine.save(productLineItem);
  }
}

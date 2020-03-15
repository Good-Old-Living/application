package meru.erp.sales.lifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.ProductSalesReport;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.mdm.catalog.UOM;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;

public class ProductSalesReportListener extends BusinessAppEntityLifeCycle<ProductSalesReport> {

  @Override
  public List<ProductSalesReport> preGet(EntityQuery<ProductSalesReport> resourceFilter) {

    Long catId = (Long) resourceFilter.getQueryParameterValue("product.productCategory.id");
    Object fromDateTime = resourceFilter.getQueryParameterValue("fromDateTime");

    List<ProductSalesReport> salesReportList = new ArrayList<>();

    EntityQuery<SalesOrder> salesOrderQuery = appEngine.createQuery(SalesOrder.class);

    salesOrderQuery.addQueryParameter("createdOn",
                                      AttributeOperator.GREATER_THAN_OR_EQUALS,
                                      fromDateTime);

    List<SalesOrder> salesOrders = appEngine.get(salesOrderQuery);
    Map<Long, ProductOrderQuantity> prdQuantityMap = new HashMap<>();

    salesOrders.forEach((salesOrder) -> {

      System.out.println(">>>> " + salesOrder.getCode());
      consolidateProducts(salesOrder,
                          catId,
                          prdQuantityMap);

    });

    prdQuantityMap.forEach((productId,
                            quantity) -> {

      ProductSalesReport prdSalesReport = new ProductSalesReport(quantity.product, quantity.code, quantity.toNumber());
      salesReportList.add(prdSalesReport);
    });
    return salesReportList;

  }

  private void consolidateProducts(SalesOrder salesOrder,
                                   Long poductCategoryId,
                                   Map<Long, ProductOrderQuantity> prdQuantityMap) {

    EntityQuery<SalesOrderLineItem> salesOrderLIQuery = appEngine.createQuery(SalesOrderLineItem.class);
    salesOrderLIQuery.addQueryParameter("salesOrderId",
                                        salesOrder.getId());
    salesOrderLIQuery.addQueryParameter("productLineItem.product.productCategory.id",
                                        poductCategoryId);

    List<SalesOrderLineItem> soLineItems = appEngine.get(salesOrderLIQuery);
    soLineItems.forEach((salesOrderLineItem) -> {

      ProductLineItem productLineItem = salesOrderLineItem.getProductLineItem();
      Product product = productLineItem.getProduct();
      Long productId = product.getId();
      ProductOrderQuantity prdQuantity = prdQuantityMap.get(productId);

      if (prdQuantity == null) {
        prdQuantity = new ProductOrderQuantity(productLineItem);
        prdQuantityMap.put(productId,
                           prdQuantity);
      } else {
        prdQuantity.setQuantity(productLineItem);
      }

    });

  }

  class ProductOrderQuantity {
    Product product;
    String code;
    float quantity;

    public ProductOrderQuantity(ProductLineItem productLineItem) {
      this.product = productLineItem.getProduct();

      int index = productLineItem.getCode().indexOf('-');
      if (index == -1) {
        code = productLineItem.getCode();
      } else {
        code = productLineItem.getCode().substring(0,
                                                   index);
      }
      setQuantity(productLineItem);
    }

    public void setQuantity(ProductLineItem productLineItem) {
      UOM uom = UOM.getUnitOfMessure(productLineItem.getUnitOfMeasure().getValue());
      quantity += uom.toFloatQuantity(productLineItem);
    }

    public Number toNumber() {

      if ((quantity % 1) == 0) {
        return new Integer((int) quantity);
      }

      return new Float(quantity);

    }
  }

}

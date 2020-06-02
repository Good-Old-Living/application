package meru.erp.sales.lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    Map<String, ProductOrderQuantity> prdQuantityMap = new TreeMap<>();

    salesOrders.forEach((salesOrder) -> {

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
                                   Map<String, ProductOrderQuantity> prdQuantityMap) {

    EntityQuery<SalesOrderLineItem> salesOrderLIQuery = appEngine.createQuery(SalesOrderLineItem.class);
    
    salesOrderLIQuery.addQueryParameter("salesOrderId",
                                        salesOrder.getId());
    salesOrderLIQuery.addQueryParameter("productLineItem.product.productCategory.id",
                                        poductCategoryId);

    List<SalesOrderLineItem> soLineItems = appEngine.get(salesOrderLIQuery);
    soLineItems.forEach((salesOrderLineItem) -> {

      ProductLineItem productLineItem = salesOrderLineItem.getProductLineItem();
      Product product = productLineItem.getProduct();
      String productName = product.getName();
      ProductOrderQuantity prdQuantity = prdQuantityMap.get(productName);
       
      if (prdQuantity == null) {
        prdQuantity = new ProductOrderQuantity(salesOrderLineItem);
        prdQuantityMap.put(productName,
                           prdQuantity);
      } else {
        prdQuantity.setQuantity(salesOrderLineItem);
      }

    });

  }

  class ProductOrderQuantity {
    Product product;
    String code;
    float quantity;

    public ProductOrderQuantity(SalesOrderLineItem soLineItem) {
      
      ProductLineItem productLineItem = soLineItem.getProductLineItem();
      product = productLineItem.getProduct();
      
      int index = productLineItem.getCode().indexOf('-');
      if (index == -1) {
        code = productLineItem.getCode();
      } else {
        code = productLineItem.getCode().substring(0,
                                                   index);
      }
      setQuantity(soLineItem);
    }

    public void setQuantity(SalesOrderLineItem soLineItem) {
      ProductLineItem productLineItem = soLineItem.getProductLineItem();
      UOM uom = UOM.getUnitOfMessure(productLineItem.getUnitOfMeasure().getValue());
      quantity += uom.toFloatQuantity(productLineItem, soLineItem.getQuantity());
    }

    public Number toNumber() {

      if ((quantity % 1) == 0) {
        return new Integer((int) quantity);
      }

      return new Float(quantity);

    }
  }

}

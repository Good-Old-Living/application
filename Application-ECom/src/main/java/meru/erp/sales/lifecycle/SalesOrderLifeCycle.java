package meru.erp.sales.lifecycle;

import java.util.List;

import app.domain.AppEntityState;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.app.AppRequest;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.mdm.catalog.lifecycle.ProductLineItemLifeCycle;
import meru.erp.sales.SalesOrderBag;
import meru.erp.sales.SalesOrderBagProvider;
import meru.erp.sales.SalesOrderDocumentId;
import meru.erp.sales.SalesOrderState;
import meru.exception.AppEntityWarning;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;
import meru.sys.uid.key.RandomIntegerKeyGenerator;
import meru.sys.uid.key.RandomKeyGenerator;

public class SalesOrderLifeCycle extends BusinessAppEntityLifeCycle<SalesOrder> {

  public static final String SEQ_SALES_ORDER_ID = "SalesOrder.Id";
  public static final String SEQ_SALES_INVOICE_ID = "Invoice.Id";
  public static final String SEQ_PAYMENT_GATEWAY_ID = "PaymentGateway.Id";

  private SalesOrderBagProvider mSalesOrderBagProvider;

  private ProductLineItemLifeCycle mProductLineItemLifeCycle;

  private RandomKeyGenerator mRandomKeyGenerator;
  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  private String notifyMobile;
  // private SalesOrderEmail orderEmail;

  @Override
  public void init() {

    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);

    mRandomKeyGenerator = new RandomIntegerKeyGenerator((byte) 4);

    //orderEmail = new SalesOrderEmail(appConfig, appContext);
    notifyMobile = appConfig.getProperty("sms.order.mobile");
  }

  public void setSalesOrderBagProvider(SalesOrderBagProvider salesOrderBagProvider) {

    mSalesOrderBagProvider = salesOrderBagProvider;

  }

  @Override
  public SalesOrder postGet(SalesOrder salesOrder) {

    if (AppRequest.currentRequest().existsParameter("cancel")) {

      cancelSalesOrder(salesOrder);

    }

    return salesOrder;
  }

  @Override
  public boolean preCreate(SalesOrder salesOrder) {

    if (mergeSalesOrderIfAlreadyExists(salesOrder)) {
      return false;
    }

    salesOrder.setSessionId(getSessionId());

    // Long paymentMethodId = salesOrder.getPaymentMethod().getId();

    //if (paymentMethodId == null || paymentMethodId == 1) {
    salesOrder.setState(getSalesOrderState(SalesOrderState.New.getCode()));
    setOrderId(salesOrder);
    salesOrder.setCode(mRandomKeyGenerator.getKey());
    //} else {
    // salesOrder.setState(getSalesOrderState(SalesOrderState.PendingPayment.getCode()));
    // long transSeq = appEngine.nextSequenceNumber(SEQ_PAYMENT_GATEWAY_ID);
    // salesOrder.setTransactionId(new
    // PaymentDocumentId(transSeq).toString());
    //}

    salesOrder.setPaymentReceivedBoolean(false);
    salesOrder.setCreatedOn(mSystemCalendar.getCalendar());

    salesOrder.setAmount(0F);

    return true;

  }

  @Override
  public Object postCreate(SalesOrder salesOrder) {

    createOrderItems(salesOrder,
                     mSalesOrderBagProvider.getSalesOrderBag(salesOrder));

    try {
      sendSMS("OrderPlaced.txt",
              notifyMobile,
              salesOrder);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // if (SalesOrderState.PendingPayment.getCode() == salesOrder.getState()
    // .getCode()) {

    // return new PGPayment(salesOrder,
    // mMerchantId,
    // mRedirectURL,
    // mWorkingKey);
    // }

    return salesOrder;
  }

  private boolean mergeSalesOrderIfAlreadyExists(SalesOrder salesOrder) {

    SalesOrderBag<SalesOrderLineItem> salesOrderBag = mSalesOrderBagProvider.getSalesOrderBag(salesOrder);

    EntityQuery<SalesOrder> query = appEngine.createQuery(SalesOrder.class);
    query.addQueryParameter("customerId",
                            salesOrder.getCustomerId());
    query.addQueryParameter("deliveryAddress.id",
                            salesOrder.getDeliveryAddress().getId());
    query.addQueryParameter("state.code",
                            SalesOrderState.New.getCode());

    SalesOrder existingSalesOrder = appEngine.getFirst(query);
    List<SalesOrderLineItem> lineItems = salesOrderBag.getLineItems();
    if (existingSalesOrder != null) {

      EntityQuery<SalesOrderLineItem> soLineItemQuery = appEngine.createQuery(SalesOrderLineItem.class);
      soLineItemQuery.addQueryParameter("salesOrderId",
                                        existingSalesOrder.getId());

      List<SalesOrderLineItem> existingLineItems = appEngine.get(soLineItemQuery);

      for (SalesOrderLineItem lineItem : lineItems) {

        boolean found = false;
        for (SalesOrderLineItem existingLineItem : existingLineItems) {

          if (existingLineItem.getProductLineItem().getId().equals(lineItem.getProductLineItem().getId())) {
            existingLineItem.setDiscount(lineItem.getDiscount());
            existingLineItem.setDiscountType(lineItem.getDiscountType());
            existingLineItem.setNetQuantity(lineItem.getNetQuantity());
            existingLineItem.setNotes(lineItem.getNotes());
            existingLineItem.setQuantity(lineItem.getQuantity());
            existingLineItem.setTotalPrice(lineItem.getTotalPrice());
            existingLineItem.setUnitMrp(lineItem.getUnitMrp());
            existingLineItem.setUnitPrice(lineItem.getUnitPrice());
            appEngine.save(existingLineItem);
            found = true;
            break;
          }
        }

        if (!found) {
          lineItem.setSalesOrderId(existingSalesOrder.getId());
          appEngine.save(lineItem);
        }
      }

      mSalesOrderBagProvider.clearSalesOrderBag();

      sendSMS("OrderMerged.txt",
              notifyMobile,
              existingSalesOrder);

      throw new AppEntityWarning("OrderMerged", existingSalesOrder);

    }

    return false;
  }

  public void createOrderItems(SalesOrder salesOrder,
                               SalesOrderBag<SalesOrderLineItem> salesOrderBag) {

    Long orderId = salesOrder.getId();

    List<SalesOrderLineItem> lineItems = salesOrderBag.getLineItems();

    if (lineItems.isEmpty()) {
      throw new AppException("No items in the cart. Please check the cart or login again to checkout");
    }

    for (SalesOrderLineItem lineItem : lineItems) {
      ProductLineItem prodLineItem = appEngine.get(ProductLineItem.class,
                                                   lineItem.getProductLineItem().getId());
      mProductLineItemLifeCycle.reduceAvailableQuantity(prodLineItem,
                                                        lineItem.getQuantity());

      lineItem.setSalesOrderId(orderId);
      appEngine.save(lineItem);

    }

    salesOrder.setAmount(salesOrderBag.getGrandTotal());

    //    Customer customer = appEngine.get(Customer.class,
    //                                      salesOrder.getCustomerId());
    //    SendEmail sendEmail = orderEmail.createMail(salesOrder,
    //                                                customer,
    //                                                salesOrderBag);
    //    if (sendEmail.getTos() != null) {
    //      appEngine.save(sendEmail);
    //    }

    // if (SalesOrderState.Received.getCode() == salesOrder.getState()
    // .getCode()) {
    //
    // createInvoice(salesOrder);

    /*
     * createMail(salesOrder, shoppingBag);
     */
    // updateCustomerLoyalty(salesOrder);

    // }

    mSalesOrderBagProvider.clearSalesOrderBag();
  }

  private void setOrderId(SalesOrder salesOrder) {

    long orderSeq = appEngine.nextSequenceNumber(SEQ_SALES_ORDER_ID);
    salesOrder.setOrderId(new SalesOrderDocumentId(orderSeq).toString());

  }

  private AppEntityState getSalesOrderState(int code) {
    return getAppEntityState(SalesOrder.class.getSimpleName(),
                             code);
  }

  // private SalesInvoice createInvoice(SalesOrder salesOrder) {
  // SalesInvoice invoice = new SalesInvoice();
  // invoice.setSalesOrder(salesOrder);
  // invoice.setNumber(SalesOrderDocumentId.getInvoiceNo(salesOrder.getOrderId()));
  // appEngine.save(invoice);
  // return invoice;
  // }

  public void cancelSalesOrder(SalesOrder salesOrder) {

    SalesOrderState currentState = SalesOrderState.getOrderState(salesOrder.getState().getCode());
    if (currentState.isTransitionAllowed(SalesOrderState.CustomerCancelled)) {
      // EntityQuery<SalesOrderLineItem> query =
      // appEngine.createQuery(SalesOrderLineItem.class);
      // query.addQueryParameter("salesOrderId", orderId);
      // List<SalesOrderLineItem> lineItems = appEngine.get(query);
      // for (SalesOrderLineItem lineItem : lineItems) {
      // appEngine.remove(SalesOrderLineItem.class, lineItem.getId());
      // }

      salesOrder.setState(getSalesOrderState(SalesOrderState.CustomerCancelled.getCode()));
      appEngine.save(salesOrder);
    }

  }

  /*
   * private void popuplateSalesOrderLineItem(SalesOrderLineItem lineItem,
   * SalesOffer salesOffer) {
   * 
   * ProductLineItem productItem = lineItem.getProductLineItem();
   * 
   * if (lineItem.getUnitMrp() == null || lineItem.getUnitMrp() == 0) {
   * lineItem.setUnitMrp(productItem.getMrp());
   * 
   * Tax tax = mProductLineItemLifeCycle.getTax(productItem); if (tax == null) {
   * lineItem.setTaxRate(0F); } else { lineItem.setTaxRate(tax.getRate()); } }
   * 
   * Float discount = null; PropertyGroup discountType = null;
   * 
   * if (lineItem.getDiscount() == null) {
   * 
   * if (salesOffer != null && salesOffer.getDiscount() >
   * lineItem.getDiscount()) { discount = salesOffer.getDiscount(); discountType
   * = salesOffer.getDiscountType(); } else { discount =
   * productItem.getDiscount(); discountType = productItem.getDiscountType(); }
   * 
   * lineItem.setDiscount(discount); lineItem.setDiscountType(discountType); }
   * else {
   * 
   * discount = lineItem.getDiscount(); discountType =
   * lineItem.getDiscountType(); }
   * 
   * float unitPrice = SalesOrderBag.getUnitPrice(lineItem.getUnitMrp(),
   * discount, discountType);
   * 
   * lineItem.setUnitPrice(unitPrice);
   * 
   * float totalPrice = SalesOrderBag.getTotalPrice(lineItem.getUnitMrp(),
   * (lineItem.getNetQuantity() == null) ? lineItem.getQuantity() :
   * lineItem.getNetQuantity(), lineItem.getDiscount(),
   * lineItem.getDiscountType());
   * 
   * lineItem.setTotalPrice(totalPrice);
   * 
   * if (lineItem.getTaxRate() == null || lineItem.getTaxRate() == -1) {
   * 
   * Tax tax = mProductLineItemLifeCycle.getTax(productItem); if (tax == null) {
   * lineItem.setTaxRate(0F); } else { lineItem.setTaxRate(tax.getRate()); }
   * 
   * } }
   */
}

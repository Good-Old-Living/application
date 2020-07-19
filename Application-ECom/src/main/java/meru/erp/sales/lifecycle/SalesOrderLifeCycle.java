package meru.erp.sales.lifecycle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.domain.AppEntityState;
import app.domain.PropertyGroup;
import app.erp.mdm.bp.Customer;
import app.erp.mdm.bp.CustomerGroup;
import app.erp.mdm.bp.CustomerWallet;
import app.erp.mdm.bp.CustomerWalletAmount;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.PaymentTransaction;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.app.AppRequest;
import meru.app.service.AppServiceManager;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.mdm.bp.PaymentDocumentId;
import meru.erp.mdm.bp.lifecycle.CustomerWalletAmountLifeCycle;
import meru.erp.mdm.catalog.lifecycle.ProductLineItemLifeCycle;
import meru.erp.sales.SalesOrderBag;
import meru.erp.sales.SalesOrderBagProvider;
import meru.erp.sales.SalesOrderDocumentId;
import meru.erp.sales.SalesOrderState;
import meru.exception.AppEntityWarning;
import meru.exception.AppException;
import meru.payment.razorpay.RazorPay;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;
import meru.sys.uid.key.RandomIntegerKeyGenerator;
import meru.sys.uid.key.RandomKeyGenerator;

public class SalesOrderLifeCycle extends BusinessAppEntityLifeCycle<SalesOrder> {

  public static final String SEQ_SALES_ORDER_ID = "SalesOrder.Id";
  public static final String SEQ_SALES_INVOICE_ID = "Invoice.Id";
  public static final String SEQ_PAYMENT_GATEWAY_ID = "PaymentGateway.Id";

  private SalesOrderBagProvider mSalesOrderBagProvider;
  private SalesOrderSplitter salesOrderSplitter;

  private ProductLineItemLifeCycle mProductLineItemLifeCycle;
  private CustomerWalletAmountLifeCycle customerWalletAmountLifeCycle;

  private RandomKeyGenerator mRandomKeyGenerator;
  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  private String notifyMobile;
  private int walletAmountThreshold = 50;
  private String insufficientBalanceMsg;
  // private SalesOrderEmail orderEmail;
  private PropertyGroup paymentMode;

  private static SalesOrderBlockComparator blockComparator = new SalesOrderBlockComparator();

  @Override
  public void init() {

    walletAmountThreshold = Integer.parseInt(AppServiceManager.getInstance()
                                                              .getAppProperty("app.order.walletThreshold"));

    insufficientBalanceMsg = AppServiceManager.getInstance().getAppProperty("app.error.insufficent_balance");

    if (insufficientBalanceMsg == null) {

      insufficientBalanceMsg = "Insuffcient wallet balance";

    }
    salesOrderSplitter = new SalesOrderSplitter(appEngine);

    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);

    customerWalletAmountLifeCycle = appEngine.getEntityLifeCycle(CustomerWalletAmount.class,
                                                                 CustomerWalletAmountLifeCycle.class);

    mRandomKeyGenerator = new RandomIntegerKeyGenerator((byte) 4);

    //orderEmail = new SalesOrderEmail(appConfig, appContext);
    notifyMobile = appConfig.getProperty("sms.order.mobile");

    //Wallet payment mode
    //    paymentMode = appEngine.getEntity(PropertyGroup.class,
    //                                      253L);
  }

  public void setSalesOrderBagProvider(SalesOrderBagProvider salesOrderBagProvider) {

    mSalesOrderBagProvider = salesOrderBagProvider;

  }

  @Override
  public SalesOrder postGet(SalesOrder salesOrder) {

    if (AppRequest.currentRequest().existsParameter("cancel")) {

      cancelSalesOrder(salesOrder);

    } else if (AppRequest.currentRequest().existsParameter("splitVeggies")) {

      return salesOrderSplitter.splitVegetableItems(salesOrder);
    } else if (AppRequest.currentRequest().existsParameter("generateRazorPayOrderId")) {

      String payOrderId = RazorPay.createOrder(salesOrder.getTransactionId(),
                                               salesOrder.getAmount());
      salesOrder.setPaymentOrderId(payOrderId);
      appEngine.save(salesOrder);
      return salesOrder;
    }

    return salesOrder;
  }

  @Override
  public boolean preCreate(SalesOrder salesOrder) {

    //check if the order is split from veggies
    if (salesOrder.getOrderId() != null && salesOrder.getOrderId().contains("-")) {
      return true;
    }

    //checkWalletBalance(salesOrder);

    salesOrder.setSessionId(getSessionId());

    salesOrder.setState(getSalesOrderState(SalesOrderState.New.getCode()));

    if (salesOrder.getPaymentMode() == null) {
      PropertyGroup paymentMode = appEngine.getEntity(PropertyGroup.class, Long.valueOf(251));
      salesOrder.setPaymentMode(paymentMode);
    }
    
    setOrderId(salesOrder);
    salesOrder.setCode(mRandomKeyGenerator.getKey());
    salesOrder.setPaymentReceivedBoolean(false);
    salesOrder.setCreatedOn(mSystemCalendar.getCalendar());

    salesOrder.setAmount(0F);

    return true;

  }

  @Override
  public boolean preModify(SalesOrder salesOrder) {

    //Delivered Status

    if (salesOrder.getState().getCode() == 6) {

      if (salesOrder.getAmount() == 0) {
        return true;
      }
      
      
      if (!customerWalletAmountLifeCycle.hasMoney(Long.valueOf(salesOrder.getCustomerId()))) {
        return true;
      }

      SalesOrder extSalesOrder = appEngine.get(SalesOrder.class,
                                               salesOrder.getId());

      if (extSalesOrder.getState().getCode() != 6) {

        long customerId = Long.valueOf(salesOrder.getCustomerId());
        int amount = Math.round(salesOrder.getAmount());

        customerWalletAmountLifeCycle.reduceMoney(customerId,
                                                  amount,
                                                  salesOrder.getOrderId());
      }
    }
    
    return true;
  }

  @Override
  public Object postCreate(SalesOrder salesOrder) {

    //Order is split
    if (salesOrder.getOrderId().contains("-")) {
      return salesOrder;
    }

    createOrderItems(salesOrder,
                     mSalesOrderBagProvider.getSalesOrderBag(salesOrder));

    long paymentModeId = salesOrder.getPaymentMode().getId();
    if (paymentModeId == 253) {
      salesOrder.setState(getSalesOrderState(SalesOrderState.PendingPayment.getCode()));
      String paymentId = getPaymentId();
      salesOrder.setTransactionId(paymentId);

      String payOrderId = RazorPay.createOrder(paymentId,
                                               salesOrder.getAmount());
      salesOrder.setPaymentOrderId(payOrderId);
    } else if (paymentModeId == 252) {
      salesOrder.setState(getSalesOrderState(SalesOrderState.PendingPayment.getCode()));
      String paymentId = getPaymentId();
      salesOrder.setTransactionId(paymentId);
      salesOrder.setPaymentOrderId(paymentId);
    }

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

  private String getPaymentId() {
    long paymentId = appEngine.nextSequenceNumber(SEQ_PAYMENT_GATEWAY_ID);
    return new PaymentDocumentId(paymentId).toString();
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

      try {
        sendSMS("OrderMerged.txt",
                notifyMobile,
                existingSalesOrder);
      } catch (Exception e) {
        e.printStackTrace();
      }

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

    CustomerWallet wallet = customerWalletAmountLifeCycle.getWallet(Long.valueOf(salesOrder.getCustomerId()));
    if (wallet.getAmount() > 0) {
      int amountToDeduct = salesOrderBag.reduceWalletAmount(wallet.getAmount());
      PaymentTransaction paymentTransaction = new PaymentTransaction();
      paymentTransaction.setWalletAmountDeducted(amountToDeduct);
      salesOrder.setPaymentTransaction(paymentTransaction);

      customerWalletAmountLifeCycle.reduceMoney(Long.valueOf(salesOrder.getCustomerId()),
                                                amountToDeduct,
                                                salesOrder.getOrderId());
    }

    float amount = salesOrderBag.getGrandTotal();

    salesOrder.setAmount(amount);

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

  @Override
  public List<SalesOrder> postGet(EntityQuery<SalesOrder> resourceFilter,
                                  List<SalesOrder> entityList) {

    String orderBy = (String) resourceFilter.getQueryParameterValue("customOrderBy");
    if (orderBy != null && orderBy.equals("block")) {
      Collections.sort(entityList,
                       blockComparator);
    }
    return entityList;
  }

  private void checkWalletBalance(SalesOrder salesOrder) {

    long customerId = Long.valueOf(salesOrder.getCustomerId());

    Customer customer = appEngine.getEntity(Customer.class,
                                            customerId);

    if (customer == null) {
      throw new AppException("Unable to process your request at this time (" + customerId + ")");
    }

    CustomerGroup customerGroup = customer.getGroup();
    if (customerGroup != null && customerGroup.walletExempted()) {
      return;
    }

    EntityQuery<CustomerWallet> entityQuery = appEngine.createQuery(CustomerWallet.class);
    //TODO convert salesorder.customerid to long
    entityQuery.addQueryParameter("customerId",
                                  customerId);
    CustomerWallet wallet = appEngine.getFirst(entityQuery);

    if (wallet == null) {
      wallet = new CustomerWallet();
      wallet.setAmount(0);
      wallet.setCustomerId(customerId);
      wallet.setCreatedOn(mSystemCalendar.getCalendar());
      appEngine.save(wallet);
    }

    SalesOrderBag<SalesOrderLineItem> salesOrderBag = mSalesOrderBagProvider.getSalesOrderBag(salesOrder);
    float amount = salesOrderBag.getTotalAmount();

    EntityQuery<SalesOrder> soQuery = appEngine.createQuery(SalesOrder.class);
    soQuery.addQueryParameter("customerId",
                              salesOrder.getCustomerId());
    soQuery.addQueryParameter("state.id",
                              AttributeOperator.IN,
                              "1,2");
    List<SalesOrder> existingSOs = appEngine.getEntities(soQuery);

    for (SalesOrder so : existingSOs) {
      amount += so.getAmount();
    }

    float total = wallet.getAmount() - amount;

    if (total < walletAmountThreshold) {
      throw new AppException(insufficientBalanceMsg);
    }

  }

  private static class SalesOrderBlockComparator implements Comparator<SalesOrder> {

    @Override
    public int compare(SalesOrder o1,
                       SalesOrder o2) {

      String text1 = o1.getDeliveryAddressText();
      String text2 = o2.getDeliveryAddressText();
      String o1Block = (text1 == null) ? o1.getDeliveryAddress().getHousingComplexAddress().getBlock() : text1;
      String o2Block = (text2 == null) ? o2.getDeliveryAddress().getHousingComplexAddress().getBlock() : text2;

      return o1Block.compareToIgnoreCase(o2Block);
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

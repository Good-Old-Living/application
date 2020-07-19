package app.erp.sales;

import app.domain.AppEntity;

public class SalesOrderPayment extends AppEntity {

  private Long salesOrderId;
  
  private Long paymentModeId;
  
  private Long customerId;

  private java.lang.String paymentOrderId;

  private java.lang.String paymentId;

  public Long getSalesOrderId() {

    return salesOrderId;
  }

  public void setSalesOrderId(Long salesOrderId) {

    this.salesOrderId = salesOrderId;
  }
  
  public Long getPaymentModeId() {

    return paymentModeId;
  }

  public void setPaymentModeId(Long paymentModeId) {

    this.paymentModeId = paymentModeId;
  }
  
  
  public Long getCustomerId() {

    return customerId;
  }

  public void setCustomerId(Long customerId) {

    this.customerId = customerId;
  }

  public java.lang.String getPaymentOrderId() {

    return paymentOrderId;
  }

  public void setPaymentOrderId(java.lang.String paymentOrderId) {

    this.paymentOrderId = paymentOrderId;
  }

  public java.lang.String getPaymentId() {

    return paymentId;
  }

  public void setPaymentId(java.lang.String paymentId) {

    this.paymentId = paymentId;
  }
}

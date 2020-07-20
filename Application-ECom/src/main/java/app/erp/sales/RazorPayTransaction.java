package app.erp.sales;

import app.domain.AppEntity;

public class RazorPayTransaction extends AppEntity {

  private java.lang.String paymentOrderId;

  private java.lang.String paymentId;

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

package app.erp.sales;

import app.domain.AppEntity;

public class RazorPayTransaction extends AppEntity {

  private java.lang.String sessionId;
  private java.lang.String customerId;

  private java.lang.String paymentOrderId;

  private java.lang.String transactionId;

  private float amount;

  public java.lang.String getSessionId() {
    return sessionId;
  }

  public void setSessionId(java.lang.String sessionId) {
    this.sessionId = sessionId;
  }

  public java.lang.String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(java.lang.String customerId) {
    this.customerId = customerId;
  }

  public java.lang.String getPaymentOrderId() {

    return paymentOrderId;
  }

  public void setPaymentOrderId(java.lang.String paymentOrderId) {

    this.paymentOrderId = paymentOrderId;
  }

  public java.lang.String getTransactionId() {

    return transactionId;
  }

  public void setTransactionId(java.lang.String transactionId) {

    this.transactionId = transactionId;
  }

  public float getAmount() {
    return amount;

  }

  public void setAmount(float amount) {
    this.amount = amount;
  }
}

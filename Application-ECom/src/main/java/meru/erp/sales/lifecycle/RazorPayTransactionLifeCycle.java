package meru.erp.sales.lifecycle;

import java.util.logging.Logger;

import app.erp.sales.RazorPayTransaction;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.mdm.bp.PaymentDocumentId;
import meru.logger.LoggerFactory;
import meru.payment.razorpay.RazorPay;

public class RazorPayTransactionLifeCycle extends BusinessAppEntityLifeCycle<RazorPayTransaction> {

  private Logger logger = LoggerFactory.getLogger(RazorPayTransactionLifeCycle.class.getPackage().getName());

  public static final String SEQ_PAYMENT_GATEWAY_ID = "PaymentGateway.Id";

  @Override
  public boolean preCreate(RazorPayTransaction rpTransaction) {

    String transactionId = getPaymentId();

    String payOrderId = RazorPay.createOrder(transactionId,
                                             rpTransaction.getAmount());

    rpTransaction.setTransactionId(transactionId);
    rpTransaction.setPaymentOrderId(payOrderId);

    try {

      String msg = String.format("Initiating RazorPay payment with [Session Id: %s, Customer Id : %s, Transaction Id : %s, Payment Order Id : %s]",
                                 rpTransaction.getSessionId(),
                                 rpTransaction.getCustomerId(),
                                 transactionId,
                                 payOrderId);
      logger.info(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  private String getPaymentId() {
    long paymentId = appEngine.nextSequenceNumber(SEQ_PAYMENT_GATEWAY_ID);
    return new PaymentDocumentId(paymentId).toString();
  }
}

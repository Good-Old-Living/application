package meru.erp.mdm.bp.lifecycle;

import com.razorpay.RazorpayException;

import app.erp.mdm.bp.Customer;
import app.erp.mdm.bp.CustomerPayment;
import app.erp.mdm.bp.RazorPayPayment;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.erp.mdm.bp.PaymentDocumentId;
import meru.exception.AppException;
import meru.payment.razorpay.RazorPay;
import meru.sys.SystemCalendar;

public class RazorPayPaymentLifeCycle extends AbstractEntityLifeCycle<RazorPayPayment> {

  public static final String SEQ_PAYMENT_GATEWAY_ID = "PaymentGateway.Id";
  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(RazorPayPayment payment) {

    Customer customer = appEngine.getEntity(Customer.class,
                                            payment.getCustomerId());

    long paymentSeq = appEngine.nextSequenceNumber(SEQ_PAYMENT_GATEWAY_ID);
    String transactionId = new PaymentDocumentId(paymentSeq).toString();
    payment.setCustomerId(customer.getId());
    payment.setTransactionId(transactionId);
    payment.setStatus("In-Process");
    payment.setCreatedOn(mSystemCalendar.getCalendar());

    String orderId = RazorPay.createOrder(transactionId,
                                          payment.getAmount());
    payment.setOrderId(orderId);

    return true;
  }

}

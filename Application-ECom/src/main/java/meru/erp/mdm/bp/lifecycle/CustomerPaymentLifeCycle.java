package meru.erp.mdm.bp.lifecycle;

import app.erp.mdm.bp.Customer;
import app.erp.mdm.bp.CustomerPayment;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.erp.mdm.bp.PaymentDocumentId;
import meru.payment.paytm.PayTMPayment;
import meru.payment.paytm.PayTMStaging;
import meru.sys.SystemCalendar;

public class CustomerPaymentLifeCycle extends AbstractEntityLifeCycle<CustomerPayment> {

  public static final String SEQ_PAYMENT_GATEWAY_ID = "PaymentGateway.Id";
  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(CustomerPayment payment) {

    Customer customer = appEngine.getEntity(Customer.class,
                                            payment.getCustomerId());

    long paymentSeq = appEngine.nextSequenceNumber(SEQ_PAYMENT_GATEWAY_ID);
    String transactionId = new PaymentDocumentId(paymentSeq).toString();
    payment.setTransactionId(transactionId);
    payment.setStatus("In-Process");
    payment.setCreatedOn(mSystemCalendar.getCalendar());

    PayTMPayment paytmPaymenet = new PayTMStaging();
    String checksum = paytmPaymenet.getChecksum(transactionId,
                                                String.valueOf(payment.getCustomerId()),
                                                customer.getMobile(),
                                                customer.getEmail(),
                                                payment.getAmount());

    payment.setChecksum(checksum);

    return true;
  }

  @Override
  public boolean preModify(CustomerPayment payment) {
    
    return true;
  }
}

package meru.payment.razorpay;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONObject;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import meru.exception.AppException;

public class RazorPay {

  //private static final String KEY_ID = "rzp_live_CxssayBNYmb52G";
  //private static final String KEY_SECRET = "yOK8uj0ad0jOy5l5iM92fQPV";
  private static final String KEY_ID = "rzp_test_SCdWZZP1AeijOH";
  private static final String KEY_SECRET = "02NZcvrhbv3NT1Wcche1ibg8";
  
  public static String createOrder(String paymentTxnId,
                            float amount) {

    try {
      RazorpayClient rpclient = new RazorpayClient(KEY_ID, KEY_SECRET);

      JSONObject options = new JSONObject();

      BigDecimal b = new BigDecimal(amount);
      BigDecimal value = b.multiply(new BigDecimal("100"));
      String amountValue = value.setScale(0,
                                          RoundingMode.UP)
                                .toString();

      options.put("amount",
                  amountValue);
      options.put("currency",
                  "INR");
      options.put("receipt",
                  paymentTxnId);
      options.put("payment_capture",
                  1); // You can enable this if you want to do Auto Capture. 
      Order order = rpclient.Orders.create(options);

      return order.get("id");
    } catch (RazorpayException e) {
      e.printStackTrace();
      throw new AppException("Unable to process the payment. " + e.getMessage(), e);
    }
  }
}

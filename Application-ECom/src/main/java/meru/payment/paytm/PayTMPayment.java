package meru.payment.paytm;

import java.util.TreeMap;

import com.paytm.pg.merchant.CheckSumServiceHelper;

import meru.exception.AppException;

public class PayTMPayment {

  public final static String MID = "krWNvv36309621153770";
  public final static String MERCHANT_KEY = "gZ#21rV82rpk!Yrn";
  public final static String INDUSTRY_TYPE_ID = "Retail";
  public final static String CHANNEL_ID = "WEB";
  public final static String WEBSITE = "https:/www.goodoldliving.com";
  public final static String PAYTM_URL = "https://securegw.paytm.in/order/process";
  public final static String CALLBACK_URL = "https://www.goodoldliving.com/payment/response.jsp";

  public String getChecksum(String transactionId,
                            String customerId,
                            String mobileNo,
                            String email,
                            int amount) {
    TreeMap<String, String> paytmParams = new TreeMap<String, String>();

    /* Find your MID in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
    paytmParams.put("MID",
                    getMID());

    /* Find your WEBSITE in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
    paytmParams.put("WEBSITE",
                    getWebsite());

    /* Find your INDUSTRY_TYPE_ID in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
    paytmParams.put("INDUSTRY_TYPE_ID",
                    INDUSTRY_TYPE_ID);

    /* WEB for website and WAP for Mobile-websites or App */
    paytmParams.put("CHANNEL_ID",
                    CHANNEL_ID);

    /* Enter your unique order id */
    paytmParams.put("ORDER_ID",
                    transactionId);

    /* unique id that belongs to your customer */
    paytmParams.put("CUST_ID",
                    customerId);

    /* customer's mobile number */
    if (mobileNo != null) {
      paytmParams.put("MOBILE_NO",
                      mobileNo);
    }
    /* customer's email */
    if (email != null) {
      paytmParams.put("EMAIL",
                      email);
    }

    /**
    * Amount in INR that is payble by customer
    * this should be numeric with optionally having two decimal points
    */
    paytmParams.put("TXN_AMOUNT",
                    String.valueOf(amount)+".00");

    /* on completion of transaction, we will send you the response on this URL */
    paytmParams.put("CALLBACK_URL",
                    CALLBACK_URL);

    
//    paytmParams.put("PAYMENT_MODE_ONLY","YES");
//    paytmParams.put("AUTH_MODE","USRPWD");
//    paytmParams.put("PAYMENT_TYPE_ID","UPI");
    
    String checksum = null;
    try {
      checksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(getMerchantKey(),
                                                                                  paytmParams);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AppException("Unable to generate checksum");
    }

    return checksum;
  }

  public String getMID() {
    return null;
  }

  public String getMerchantKey() {
    return null;
  }

  public String getIndustryType() {
    return null;
  }

  public String getWebsite() {
    return null;
  }

  public String getURL() {
    return null;
  }

}

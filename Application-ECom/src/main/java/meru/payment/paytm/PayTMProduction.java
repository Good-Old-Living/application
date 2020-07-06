package meru.payment.paytm;

public class PayTMProduction extends PayTMPayment {

  public final static String MID = "krWNvv36309621153770";
  public final static String MERCHANT_KEY = "fUfCvM@W0RdXbeH9";
  public final static String INDUSTRY_TYPE_ID = "Retail";
  public final static String CHANNEL_ID = "WEB";
  public final static String WEBSITE = "DEFAULT";
  public final static String PAYTM_URL = "https://securegw.paytm.in/order/process";

  public String getMID() {
    return MID;
  }

  public String getMerchantKey() {
    return MERCHANT_KEY;
  }

  public String getIndustryType() {
    return INDUSTRY_TYPE_ID;
  }

  public String getWebsite() {
    return WEBSITE;
  }

  public String getURL() {
    return PAYTM_URL;
  }
}

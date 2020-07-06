package meru.payment.paytm;

public class PayTMStaging extends PayTMPayment {
  
  public final static String MID="cmmYhT84098889283210";
  public final static String MERCHANT_KEY="gZ#21rV82rpk!Yrn";
  public final static String INDUSTRY_TYPE_ID="Retail";
  public final static String CHANNEL_ID="WEB";
  public final static String WEBSITE="WEBSTAGING";
  public final static String PAYTM_URL="https://securegw-stage.paytm.in/order/process"; 

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

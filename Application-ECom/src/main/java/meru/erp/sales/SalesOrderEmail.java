package meru.erp.sales;

import java.util.List;

import app.domain.comm.SendEmail;
import app.erp.mdm.bp.Customer;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.sales.SalesOrder;
import app.erp.sales.SalesOrderLineItem;
import meru.app.AppContext;
import meru.app.AppProperty;
import meru.app.Currency;
import meru.app.config.AppConfig;
import meru.erp.mdm.catalog.ProductLineItemDN;
import meru.messaging.MessageState;
import meru.sys.IOSystem;
import meru.sys.SystemCalendar;
import meru.template.TemplateEngine;
import meru.template.TemplateMultiData;
import meru.template.TemplateStringData;
import meru.ui.faces.renderer.html.HtmlBuilder;

public class SalesOrderEmail {

  private static final String PROP_ORDER_MAIL = "mail.order.email";
  private static final String PROP_ORDER_MAIL_BCC = "mail.order.email.bcc";

  private AppContext appContext;
  private String orderCCMail;
  private String orderBCCMail;

  public SalesOrderEmail(AppConfig appConfig,
                         AppContext appContext) {

    this.appContext = appContext;
    orderCCMail = appConfig.getMandatoryProperty(PROP_ORDER_MAIL);
    orderBCCMail = appConfig.getMandatoryProperty(PROP_ORDER_MAIL_BCC);

  }

  public SendEmail createMail(SalesOrder salesOrder,
                              Customer customer,
                              SalesOrderBag<SalesOrderLineItem> shoppingBag) {

    String email = customer.getEmail();

    if (email == null || !email.contains("@")) {
      email = orderCCMail;
    }
    HtmlBuilder htmlBuilder = new HtmlBuilder();
    int count = 0;

    List<SalesOrderLineItem> lineItems = shoppingBag.getLineItems();

    for (SalesOrderLineItem salesOrderLineItem : lineItems) {

      ProductLineItem productItem = salesOrderLineItem.getProductLineItem();

      htmlBuilder.startElement("tr");

      htmlBuilder.startElement("td")
                 .addAttribute("width",
                               "40")
                 .addText(String.valueOf(++count))
                 .endElement();
      htmlBuilder.startElement("td")
                 .addText(ProductLineItemDN.getProductNameWithQuantity(productItem))
                 .endElement();

      htmlBuilder.startElement("td")
                 .addAttribute("align",
                               "right")
                 .addText(String.valueOf(salesOrderLineItem.getQuantity()))
                 .endElement();
      htmlBuilder.startElement("td")
                 .addAttribute("align",
                               "right")
                 .addText(String.valueOf(Currency.toString(salesOrderLineItem.getUnitMrp())))
                 .endElement();

      htmlBuilder.startElement("td")
                 .addAttribute("align",
                               "right")
                 .addText(String.valueOf(Currency.toString(salesOrderLineItem.getUnitPrice())))
                 .endElement();

      // htmlBuilder.startElement("td");
      // Float discount = salesOrderLineItem.getDiscount();
      //
      // if (discount != null && discount != 0F) {
      // htmlBuilder.addText(String.valueOf(Currency.getFormattedFloatValue(discount)));
      //
      // if (salesOrderLineItem.getDiscountType() != null) {
      // htmlBuilder.addText(salesOrderLineItem.getDiscountType()
      // .getValue(),
      // false);
      // }
      //
      // }
      //
      // htmlBuilder.endElement();
      //
      // htmlBuilder.startElement("td")
      // .addText(String.valueOf(salesOrderLineItem.getUnitPrice()))
      // .endElement();
      //
      // float savings =
      // SalesOrderBag.getSavings(salesOrderLineItem.getUnitMrp(),
      // salesOrderLineItem.getQuantity(),
      // salesOrderLineItem.getTotalPrice());
      // htmlBuilder.startElement("td")
      // .addText(String.valueOf(Currency.getFormattedFloatValue(savings)))
      // .endElement();

      htmlBuilder.startElement("td")
                 .addAttribute("align",
                               "right")
                 .addText(String.valueOf(Currency.toString(salesOrderLineItem.getTotalPrice())))
                 .endElement();

      htmlBuilder.endElement();
    }

    htmlBuilder.startElement("tr")
               .startElement("td")
               .addAttribute("colspan",
                             "8")
               .addAttribute("align",
                             "right");

    htmlBuilder.startElement("table")
               .addAttribute("border",
                             "0");

    htmlBuilder.startElement("tr")
               .startElement("td")
               .addText("Sub Total")
               .endElement()
               .startElement("td")
               .addText(":")
               .endElement()
               .startElement("td")
               .addAttribute("align",
                             "right")
               .addText(Currency.toString(shoppingBag.getSubTotal()))
               .endElement()
               .endElement();

    // htmlBuilder.startElement("tr")
    // .startElement("td")
    // .addText("Tax")
    // .endElement()
    // .startElement("td")
    // .addText(":")
    // .endElement()
    // .startElement("td")
    // .addAttribute("align",
    // "right")
    // .addText(String.valueOf(shoppingBag.getTax()))
    // .endElement()
    // .endElement();

    htmlBuilder.startElement("tr")
               .startElement("td")
               .addText("Delivery Charge")
               .endElement()
               .startElement("td")
               .addText(":")
               .endElement()
               .startElement("td")
               .addAttribute("align",
                             "right")
               .addText(Currency.toString(shoppingBag.getDeliveryCharge()))
               .endElement()
               .endElement();

    // htmlBuilder.startElement("tr")
    // .startElement("td")
    // .addText("Savings")
    // .endElement()
    // .startElement("td")
    // .addText(":")
    // .endElement()
    // .startElement("td")
    // .addAttribute("align",
    // "right")
    // .addText(String.valueOf(shoppingBag.getSavings()))
    // .endElement()
    // .endElement();

    htmlBuilder.startElement("tr")
               .startElement("td")
               .addText("Grand Total")
               .endElement()
               .startElement("td")
               .addText(":")
               .endElement()
               .startElement("td")
               .addAttribute("align",
                             "right")
               .addText(Currency.toString(shoppingBag.getTotalAmount()))
               .endElement()
               .endElement();

    htmlBuilder.endElement("table")
               .endElement("td")
               .endElement("tr");

    String template = IOSystem.read(appContext.getInputStream(AppProperty.TEMPLATE_DIR_MAIL + "NewOrderMail.html"));

    TemplateMultiData multiData = new TemplateMultiData();
    multiData.addObject("salesOrder",
                        salesOrder);
    multiData.addObject("customer",
                        customer);
    multiData.addObject("orderItems",
                        new TemplateStringData(htmlBuilder.toString()));
    String message = TemplateEngine.getText(template,
                                            multiData);

    SendEmail mail = new SendEmail();
    mail.setTos(customer.getEmail());

    mail.setCcs(orderCCMail);
    mail.setBccs(orderBCCMail);

    mail.setSubject("Thank you for placing your order with Paaril.com");
    mail.setContentType("text/html");
    mail.setSentOn(SystemCalendar.getInstance()
                                 .getUTCCalendar());

    mail.setMessage(message);
    mail.setState(MessageState.NEW.getState());
    mail.setReference("so:" + salesOrder.getId());

    return mail;
  }

}

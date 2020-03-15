package meru.erp.mdm.catalog;

import meru.app.engine.AppEngine;
import app.erp.finance.Tax;
import app.erp.mdm.catalog.ProductLineItem;

public interface ProductLineItemPriceProvider {

  Float getPrice(ProductLineItem productLineItem,
                 Tax tax,
                 AppEngine appEngine);

}

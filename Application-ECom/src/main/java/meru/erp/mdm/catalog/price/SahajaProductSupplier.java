package meru.erp.mdm.catalog.price;

import java.util.Properties;

import meru.app.AppContext;

public class SahajaProductSupplier extends ArivuProductSupplier {

  public SahajaProductSupplier(Properties properties,
                               int nameIndex,
                               int maxColumnIndex) {
    super(properties, nameIndex, maxColumnIndex);
  }

  public static ArivuProductSupplier createProductSupplier(AppContext appContext) {

    return new ArivuProductSupplier(ProductSupplier.loadProductCodes(appContext,
                                                                     "sahaja-product-codes.txt"),
        1, 3);

  }
}

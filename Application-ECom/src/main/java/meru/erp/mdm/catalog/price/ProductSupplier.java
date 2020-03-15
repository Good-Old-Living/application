package meru.erp.mdm.catalog.price;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;

import app.config.EComConfig;

public interface ProductSupplier {

  String getProductCode(Row row);

  String getProductName(Row row);

  String getFileName();

  static File getProductPriceFile(String supplier) {
    return new File(EComConfig.VEGGIES_LIST_TEMP_PATH, supplier + EComConfig.VEGGIES_LIST_FILE_NAME);
  }
}

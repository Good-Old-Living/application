package meru.erp.mdm.catalog.price;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;

import app.config.EComConfig;
import meru.app.AppContext;
import meru.app.AppProperty;
import meru.exception.AppException;

public interface ProductSupplier {

  String getProductCode(Row row);

  String getProductName(Row row);
  
  int getMaximumColumnIndex();

  //String getFileName();

  static File getProductPriceFile(String supplier) {
    return new File(EComConfig.VEGGIES_LIST_TEMP_PATH, supplier + EComConfig.VEGGIES_LIST_FILE_NAME);
  }
  
  static Properties loadProductCodes(AppContext appContext, String productCodeFileName) {
    Properties prdCodes = new Properties();
    try (InputStream inputStream = appContext.getInputStream(AppProperty.RESOURCES_DIR + productCodeFileName)) {
      prdCodes.load(inputStream);
    } catch (IOException e) {
      throw new AppException(e, "Unable to load product codes from file " + productCodeFileName);
    }
    
    return prdCodes;
  }
}

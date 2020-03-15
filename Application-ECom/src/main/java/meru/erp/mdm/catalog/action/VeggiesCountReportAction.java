package meru.erp.mdm.catalog.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import app.config.EComConfig;
import meru.app.AppProperty;
import meru.erp.ActionEntity;
import meru.erp.ActionEntity.EntityAction;
import meru.erp.mdm.catalog.price.ArivuProductSupplier;
import meru.erp.mdm.catalog.price.ProductListXLSWriter;
import meru.erp.mdm.catalog.price.SahajaProductSupplier;
import meru.exception.AppException;
import meru.sys.SystemDateTime;

public class VeggiesCountReportAction extends EntityAction {

  @Override
  public ActionEntity act(ActionEntity actionEntity) {

    String[] data = actionEntity.getData().split("[,]");

    String supplier = data[0];

    String fromDateTime = data[1];
    Calendar dateTime = SystemDateTime.parseDateTime(fromDateTime);

    ProductListXLSWriter excelWriter = null;
    
    switch (supplier) {
      
      case "Sahaja":
        excelWriter = new ProductListXLSWriter(appEngine, 4, new SahajaProductSupplier());
        break;
      case "Arivu":
        
        Properties arivuPrdCodes = new Properties();
        try (InputStream inputStream = appContext.getInputStream(AppProperty.RESOURCES_DIR+"arivu-product-codes.txt")) {
          arivuPrdCodes.load(inputStream);
        } catch (IOException e) {
          throw new AppException(e, "Unable to load product codes");
        }
        
        excelWriter = new ProductListXLSWriter(appEngine, 3, new ArivuProductSupplier(arivuPrdCodes));
        break;
        
        default :
          throw new AppException("Unknown supplier : "+supplier);
    }
    
    
    System.out.println("Generating the report");
    File file = new File(EComConfig.VEGGIES_LIST_TEMP_PATH,
                         supplier + EComConfig.VEGGIES_LIST_FILE_NAME);
    excelWriter.write(file,
                      supplier,
                      dateTime);

    return new GenerateVeggiesQty(EComConfig.APP_TEMP_FOLDER + "Order_" + supplier + EComConfig.VEGGIES_LIST_FILE_NAME);
  }

  public static class GenerateVeggiesQty extends ActionEntity {
    private String csvFile;

    public GenerateVeggiesQty(String csvFile) {
      this.csvFile = csvFile;
    }

    public String getCsvFile() {
      return csvFile;
    }
  }
}

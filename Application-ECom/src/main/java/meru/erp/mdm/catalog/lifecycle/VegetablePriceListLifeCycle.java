package meru.erp.mdm.catalog.lifecycle;

import java.io.File;

import app.config.EComConfig;
import app.erp.mdm.catalog.VegetablePriceList;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.erp.mdm.catalog.price.ArivuProductSupplier;
import meru.erp.mdm.catalog.price.ProductListXLSReader;
import meru.erp.mdm.catalog.price.SahajaProductSupplier;
import meru.exception.AppException;

public class VegetablePriceListLifeCycle extends BusinessAppEntityLifeCycle<VegetablePriceList> {

  // private ExcelReader xlsReader = new ExcelReader();
  private ProductListXLSReader sahajaProductListXLSReader;
  private ProductListXLSReader arivuProductListXLSReader;

  @Override
  public void init() {

    sahajaProductListXLSReader = new ProductListXLSReader(2, 3, 3,
        SahajaProductSupplier.createProductSupplier(appContext));
    arivuProductListXLSReader = new ProductListXLSReader(2, 3, 3,
        ArivuProductSupplier.createProductSupplier(appContext));
  }

  @Override
  public boolean preCreate(VegetablePriceList priceList) {
    init();
    File csvFile = new File(priceList.getCsvFile());

    System.out.println("Uploading veggies list " + csvFile.getAbsolutePath() + "  from " + priceList.getSupplier());

    try {
      switch (priceList.getSupplier()) {
        case "Sahaja":
          sahajaProductListXLSReader.read(appEngine,
                                          csvFile);

          break;
        case "Arivu":
          arivuProductListXLSReader.read(appEngine,
                                         csvFile);

          break;
        default:
          throw new AppException("Unknown supplier : " + priceList.getSupplier());
      }

    } finally {

      File file = new File(EComConfig.VEGGIES_LIST_TEMP_PATH,
          priceList.getSupplier() + EComConfig.VEGGIES_LIST_FILE_NAME);
      file.delete();

      if (!csvFile.renameTo(file)) {
        throw new AppException("Unable to move the file " + csvFile + " to " + file);
      }
    }
    return false;
  }

}

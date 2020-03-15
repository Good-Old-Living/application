package meru.erp.mdm.catalog.price;

import org.apache.poi.ss.usermodel.Row;

public class SahajaProductSupplier implements ProductSupplier {

  @Override
  public String getProductCode(Row row) {
    return row.getCell(1).getStringCellValue();
  }

  @Override
  public String getProductName(Row row) {
    return row.getCell(2).getStringCellValue();
  }

  @Override
  public String getFileName() {
    return "sahaja-product-list.xlsx";
  }

  
}

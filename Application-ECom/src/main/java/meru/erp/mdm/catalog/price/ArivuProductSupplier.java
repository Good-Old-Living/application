package meru.erp.mdm.catalog.price;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;

public class ArivuProductSupplier implements ProductSupplier {

  private Map<String, String> productCodeMap = new HashMap<>();

  public ArivuProductSupplier(Properties properties) {

    for (Object property : properties.keySet()) {
      productCodeMap.put(properties.getProperty((String) property),
                         (String) property);
    }

  }

  @Override
  public String getProductCode(Row row) {
    String name = row.getCell(1).getStringCellValue();
    String value = productCodeMap.get(name);

    if (value == null) {
      value = "Null";
    }

    return value;
  }
  
  @Override
  public String getProductName(Row row) {
    return row.getCell(1).getStringCellValue();
  }

  @Override
  public String getFileName() {
    return "arivu-product-list.xlsx";
  }
}

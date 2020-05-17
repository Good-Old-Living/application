package meru.erp.mdm.catalog.price;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;

public class ProductSupplierImpl implements ProductSupplier {

  private Map<String, String> productCodeMap = new HashMap<>();
  private String fileName;
  private int nameColumn;

  public ProductSupplierImpl(Properties properties,
                             String fileName,
                             int nameColumn) {

    this.fileName = fileName;
    this.nameColumn = nameColumn;

    for (Object property : properties.keySet()) {
      productCodeMap.put(properties.getProperty((String) property),
                         (String) property);
    }

  }

  @Override
  public String getProductCode(Row row) {
    String name = row.getCell(nameColumn).getStringCellValue();
    String value = productCodeMap.get(name);

    if (value == null) {
      value = "Null";
    }

    return value;
  }

  @Override
  public String getProductName(Row row) {
    return row.getCell(nameColumn).getStringCellValue();
  }

  @Override
  public String getFileName() {
    return fileName;
  }
}

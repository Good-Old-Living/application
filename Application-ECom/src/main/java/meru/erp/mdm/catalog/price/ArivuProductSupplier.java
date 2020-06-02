package meru.erp.mdm.catalog.price;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;

import meru.app.AppContext;

public class ArivuProductSupplier implements ProductSupplier {

  private Map<String, String> productCodeMap = new HashMap<>();
  private int nameIndex;
  private int maxColumnIndex;

  public ArivuProductSupplier(Properties properties,
                              int nameIndex,
                              int maxColumnIndex) {
    this.nameIndex = nameIndex;
    this.maxColumnIndex = maxColumnIndex;

    for (Object property : properties.keySet()) {
      productCodeMap.put(properties.getProperty((String) property),
                         (String) property);
    }

  }

  public static ArivuProductSupplier createProductSupplier(AppContext appContext) {

    return new ArivuProductSupplier(ProductSupplier.loadProductCodes(appContext,
                                                                     "arivu-product-codes.txt"),
        1, 3);

  }

  @Override
  public String getProductCode(Row row) {
    String name = row.getCell(nameIndex).getStringCellValue().trim();
    String value = productCodeMap.get(name);

    if (value == null) {
      value = "Null";
    }

    return value;
  }

  @Override
  public String getProductName(Row row) {
    return row.getCell(nameIndex).getStringCellValue();
  }

  @Override
  public int getMaximumColumnIndex() {
    return maxColumnIndex;
  }

}

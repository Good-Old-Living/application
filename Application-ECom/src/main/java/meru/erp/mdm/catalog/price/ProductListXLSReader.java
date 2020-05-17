package meru.erp.mdm.catalog.price;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.erp.mdm.catalog.Product;
import meru.app.engine.AppEngine;
import meru.erp.mdm.catalog.FruitVegKey;
import meru.exception.AppEntityWarning;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;
import meru.sys.JVM;

public class ProductListXLSReader {

  private int measurementIndex;
  private int priceIndex;
  private int maxUsedColumns;
  private ProductSupplier productCodeProvider;

  public ProductListXLSReader(int measurementIndex,
                              int priceIndex,
                              int maxUsedColumns,
                              ProductSupplier productCodeProvider) {
    this.measurementIndex = measurementIndex;
    this.priceIndex = priceIndex;
    this.maxUsedColumns = maxUsedColumns;
    this.productCodeProvider = productCodeProvider;
  }

  public void read(AppEngine appEngine,
                   File xlsFile) {

    VegetablePriceUploader priceUploader = new VegetablePriceUploader(appEngine);

    List<ProductWarning> unprocessedProdsList = new ArrayList<>();

    try (FileInputStream file = new FileInputStream(xlsFile); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
      //Get first/desired sheet from the workbook
      XSSFSheet sheet = workbook.getSheetAt(0);

      Iterator<Row> rowIterator = sheet.iterator();
      
      
      EntityQuery<Product> productQuery = appEngine.createQuery(Product.class);
      productQuery.addQueryParameter("productCategory.id", AttributeOperator.IN, FruitVegKey.asList());
      List<Product> productList = appEngine.get(productQuery);
      for (Product product : productList) {
        product.setIsActiveBoolean(false);
        appEngine.save(product);
      }
      
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (row.getRowNum() < maxUsedColumns) {
          continue;
        }

        String productCode = productCodeProvider.getProductCode(row);
        String measurement = row.getCell(measurementIndex).getStringCellValue();

        float price = 0;
        Cell cell = row.getCell(priceIndex);
        if (cell.getCellType() == CellType.NUMERIC) {
          price = (float) cell.getNumericCellValue();
        } else {

          try {
            price = Integer.parseInt(cell.getStringCellValue());
          } catch (NumberFormatException e) {

          }
        }

        if (!priceUploader.upload(productCode,
                                  measurement,
                                  price)) {
          unprocessedProdsList.add(new ProductWarning(productCode, productCodeProvider.getProductName(row)));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    if (!unprocessedProdsList.isEmpty()) {
      throw new AppEntityWarning("UnprocessedProducts", unprocessedProdsList);
    }
  }

  public static class ProductWarning {

    private String code;
    private String name;

    public ProductWarning(String code,
                          String name) {
      this.code = code;
      this.name = name;

    }

    public String getCode() {
      return code;
    }

    public String getName() {
      return name;
    }

    public String toString() {
      return code + " " + name + JVM.SystemProperty.NEW_LINE;
    }
  }

  public static void main(String[] args) {
    /*String fileName = "/home/nakul/Downloads/Product list.xlsx";
    new ExcelReader().read(null,
                         fileName);*/
  }

}

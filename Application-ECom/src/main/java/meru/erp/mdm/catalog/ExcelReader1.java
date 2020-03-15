package meru.erp.mdm.catalog;

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

import meru.app.engine.AppEngine;
import meru.erp.mdm.catalog.price.VegetablePriceUploader;
import meru.exception.AppEntityWarning;
import meru.sys.JVM;

public class ExcelReader1 {

  public void read(AppEngine appEngine,
                   File xlsFile) {

    System.out.println("Uploading veggies list " + xlsFile.getAbsolutePath());

    VegetablePriceUploader priceUploader = new VegetablePriceUploader(appEngine);
    List<ProductWarning> unprocessedProdsList = new ArrayList<>();

    try (FileInputStream file = new FileInputStream(xlsFile); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
      //Get first/desired sheet from the workbook
      XSSFSheet sheet = workbook.getSheetAt(0);

      Iterator<Row> rowIterator = sheet.iterator();
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (row.getRowNum() < 4) {
          continue;
        }

        String productCode = row.getCell(1).getStringCellValue();
        String measurement = row.getCell(3).getStringCellValue();

        float price = 0;
        Cell cell = row.getCell(4);
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
          unprocessedProdsList.add(new ProductWarning(productCode, row.getCell(2).getStringCellValue()));
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

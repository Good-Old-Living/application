package meru.erp.mdm.catalog.price;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.erp.sales.ProductSalesReport;
import meru.app.engine.AppEngine;
import meru.persistence.EntityQuery;

public class ProductListXLSWriter {

  private AppEngine appEngine;
  private Map<String, ProductSalesReport> reportMap = new HashMap<>();
  private int maxColumnIndex;
  private ProductSupplier productCodeProvider;

  public ProductListXLSWriter(AppEngine appEngine,
                              int maxColumnIndex,
                              ProductSupplier productCodeProvider) {
    this.appEngine = appEngine;
    this.maxColumnIndex = maxColumnIndex;
    this.productCodeProvider = productCodeProvider;
  }

  void populateReportMap(EntityQuery<ProductSalesReport> salesQuery,
                         long categoryId) {

    salesQuery.addQueryParameter("product.productCategory.id",
                                 categoryId);

    List<ProductSalesReport> salesReport = appEngine.get(salesQuery);

    for (ProductSalesReport report : salesReport) {
      reportMap.put(report.getCode(),
                    report);
    }

  }

  public void write(File xlsFile,
                    String supplier,
                    Calendar dateTime) {
    reportMap.clear();
    System.out.println("Writing to file " + xlsFile.getAbsolutePath());

    EntityQuery<ProductSalesReport> salesQuery = appEngine.createQuery(ProductSalesReport.class);
    salesQuery.addQueryParameter("fromDateTime",
                                 dateTime);

    populateReportMap(salesQuery,
                      101);
    populateReportMap(salesQuery,
                      102);
    populateReportMap(salesQuery,
                      103);

    File xlsUpdFile = new File(xlsFile.getParent(), "Order_" + xlsFile.getName());
    try (FileInputStream fileIPStream = new FileInputStream(xlsFile);
        FileOutputStream fileOPStream = new FileOutputStream(xlsUpdFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fileIPStream)) {

      XSSFSheet sheet = workbook.getSheetAt(0);

      Iterator<Row> rowIterator = sheet.iterator();
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (row.getRowNum() < maxColumnIndex) {
          continue;
        }

        String productCode = "SO" + productCodeProvider.getProductCode(row);

        Cell cell = row.getCell(maxColumnIndex + 1);
        if (cell == null) {
          cell = row.createCell(maxColumnIndex + 1);
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);

        ProductSalesReport count = reportMap.get(productCode);
        if (count != null) {
          cell.setCellValue(Double.valueOf(count.getQuantity().toString()));
        }

      }

      workbook.write(fileOPStream);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /*public static void main(String[] args) {
    String fileName = "/home/nakul/Downloads/Product list.xlsx";
    new ExcelWriter().write(fileName);
  }*/

}

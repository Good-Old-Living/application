package meru.erp.mdm.catalog.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.config.EComConfig;
import app.erp.mdm.catalog.ProductLineItem;
import meru.erp.ActionEntity;
import meru.erp.ActionEntity.EntityAction;
import meru.erp.FileEntity;
import meru.erp.mdm.catalog.FruitVegKey;
import meru.erp.mdm.catalog.UOM;
import meru.exception.AppException;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;

public class VeggiesPriceListAction extends EntityAction {

  private final String ID_LIST = FruitVegKey.OrganicFruits.getKey() + "," + FruitVegKey.OrganicVegetable.getKey() + ","
      + FruitVegKey.OrganicGreens.getKey();

  private Map<String, ProductLineItem> prdLineItemMap = new LinkedHashMap<>();

  @Override
  public ActionEntity act(ActionEntity actionEntity) {

    System.out.println("Generating the report");
    populateProductList();
    File updFile = updatePrice();
    return new FileEntity(EComConfig.APP_TEMP_FOLDER + updFile.getName());
  }

  private void populateProductList() {

    EntityQuery<ProductLineItem> prdLineItemQuery = appEngine.createQuery(ProductLineItem.class);

    prdLineItemQuery.addQueryParameter("product.productCategory.id",
                                       AttributeOperator.IN,
                                       ID_LIST);
    prdLineItemQuery.addQueryParameter("product.isActive",
                                       "Y");
    prdLineItemQuery.addQueryParameter("isActive",
                                       "Y");
    prdLineItemQuery.addQueryParameter("unitOfMeasure.value",
                                       AttributeOperator.IN,
                                       "'kg','pc','bunch'");

    List<ProductLineItem> prdLineItemList = appEngine.get(prdLineItemQuery);

    for (ProductLineItem productLineItem : prdLineItemList) {

      if (UOM.representsUnitPrice(productLineItem)) {
        String code = productLineItem.getCode();
        if (code.contains("-")) {
          code = code.substring(0,
                                code.indexOf('-'));
        }
        prdLineItemMap.put(code,
                           productLineItem);
      }

    }

  }

  void setBorder(XSSFWorkbook workbook,
                 Cell cell,
                 HorizontalAlignment alignment) {
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(alignment);
    XSSFFont font = workbook.createFont();
    font.setFontName("Calibri");
    //font.setBold(true);
    //font.setFontHeight(12);
    cellStyle.setFont(font);
    cellStyle.setBorderTop(BorderStyle.THIN);
    cellStyle.setBorderBottom(BorderStyle.THIN);
    cellStyle.setBorderLeft(BorderStyle.THIN);
    cellStyle.setBorderRight(BorderStyle.THIN);
    cell.setCellStyle(cellStyle);
  }

  private File updatePrice() {

    File xlsUpdFile = new File(EComConfig.VEGGIES_LIST_TEMP_PATH, "price_list.xlsx");
    xlsUpdFile.delete();

    try {
      xlsUpdFile.createNewFile();
    } catch (IOException e) {
      throw new AppException(e, "Unable to create the file " + xlsUpdFile);
    }

    try (FileOutputStream fileOPStream = new FileOutputStream(xlsUpdFile); XSSFWorkbook workbook = new XSSFWorkbook()) {
      
      
      XSSFSheet sheet = workbook.createSheet();

      int i = 0;
      for (Map.Entry<String, ProductLineItem> prdLineItemEntry : prdLineItemMap.entrySet()) {

        ProductLineItem prdLineItem = prdLineItemEntry.getValue();

        Row row = sheet.createRow(i++);
        Cell cell = row.createCell(0);
        cell.setCellValue(prdLineItem.getProduct().getName());
        setBorder(workbook,
                  cell,
                  HorizontalAlignment.LEFT);

        cell = row.createCell(1);
        cell.setCellValue(prdLineItem.getUnitOfMeasure().getValue());
        setBorder(workbook,
                  cell,
                  HorizontalAlignment.RIGHT);

        cell = row.createCell(2);
        cell.setCellValue(prdLineItem.getPrice().intValue());
        setBorder(workbook,
                  cell,
                  HorizontalAlignment.RIGHT);
      }

      workbook.write(fileOPStream);

      //      List<Row> rowList = new ArrayList<>(5);
      //      Iterator<Row> rowIterator = sheet.iterator();
      //      while (rowIterator.hasNext()) {
      //        Row row = rowIterator.next();
      //        if (row.getRowNum() < 4) {
      //          continue;
      //        }
      //
      //        String productCode = "SO" + row.getCell(1).getStringCellValue();
      //
      //        ProductLineItem prdLineItem = prdLineItemMap.get(productCode);
      //
      //        System.out.println(">>>>>> Code " + productCode + " " + prdLineItem);
      //        if (prdLineItem == null) {
      //          rowList.add(row);
      //          continue;
      //        }
      //
      //        Cell cell = row.getCell(4);
      //
      //        cell.setCellValue(String.valueOf(prdLineItem.getPrice().intValue()));
      //      }
      //
      //      for (Row row : rowList) {
      //        //int rowNum = row.getRowNum();
      //        sheet.removeRow(row);
      //        //        sheet.shiftRows(rowNum,
      //        //                        rowNum,
      //        //                        -1);
      //      }
      //
      //      workbook.write(fileOPStream);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return xlsUpdFile;
  }
}

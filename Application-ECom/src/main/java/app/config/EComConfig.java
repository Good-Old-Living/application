package app.config;

import java.io.File;

public class EComConfig {

  public static final String APP_TEMP_FOLDER = "/tmpfiles/";
  public static final String VEGGIES_LIST_FILE_NAME = "product-list.xlsx";
  public static final String VEGGIES_LIST_TEMP_PATH = "webapps/ROOT"+APP_TEMP_FOLDER;
  
  public static final File VEGGIES_LIST_FILE = new File(VEGGIES_LIST_TEMP_PATH + VEGGIES_LIST_FILE_NAME);

  
}

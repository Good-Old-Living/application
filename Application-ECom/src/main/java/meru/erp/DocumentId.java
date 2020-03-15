package meru.erp;

import java.util.Calendar;

import meru.sys.SystemCalendar;

public abstract class DocumentId {

  private static final String DATE_FORMAT = "yyMM";

  private static final String DB = "P";
  private static final String STATE = "01";

  private static final int ID_PADDING = 7;

  private String mId;

  public DocumentId(long id) {
    Calendar calendar = SystemCalendar.getInstance()
                                      .getCalendar();
    String date = SystemCalendar.getInstance()
                                .toCalendarString(calendar,
                                                  DATE_FORMAT);

    mId = getPrefix() + date + padId(id);
  }

  protected static final String createPrefix(String idType) {
    return DB + idType + STATE;
  }

  protected abstract String getPrefix();

  public String toString() {
    return mId;
  }

  private static String padId(long id) {

    String idStr = String.valueOf(id);

    if (idStr.length() < ID_PADDING) {
      StringBuilder strBuilder = new StringBuilder();
      for (int i = 0; i < ID_PADDING - idStr.length(); i++) {
        strBuilder.append('0');
      }
      strBuilder.append(id);

      return strBuilder.toString();
    }

    return idStr;
  }

  public static String getDocumentSequenceNo(String documentId) {
    return documentId.substring(documentId.length() - ID_PADDING);
  }

  public static void main(String[] args) {
    String id = "DBOKA0034567";
    System.out.println(getDocumentSequenceNo(id));
  }

}

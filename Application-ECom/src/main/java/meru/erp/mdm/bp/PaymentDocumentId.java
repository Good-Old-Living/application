package meru.erp.mdm.bp;

import meru.erp.DocumentId;

public class PaymentDocumentId extends DocumentId {

  private static final String PREFIX = createPrefix("P");

  public PaymentDocumentId(long id) {
      super(id);
  }

  @Override
  protected String getPrefix() {
      return PREFIX;
  }
}

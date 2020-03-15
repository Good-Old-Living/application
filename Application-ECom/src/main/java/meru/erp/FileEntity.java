package meru.erp;

public class FileEntity extends ActionEntity {
  private String file;

  public FileEntity(String file) {
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}

package meru.image;

public enum ImageSize {

  Size100x100("100x100", 100, 100),
  Size150x150("150x150", 150, 150),
  Size300x300("300x300", 300, 300);

  private String name;
  private int width;
  private int height;

  private ImageSize(String name, int width, int height) {
    this.name = name;
    this.width = width;
    this.height = height;
  }

  public String getName() {
    return name;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public static ImageSize getImageSize(String size) {
    for (ImageSize imageSize : ImageSize.values()) {
      if (imageSize.name.equals(size)) {
        return imageSize;
      }
    }

    throw new RuntimeException("Invalid image size : " + size);
  }

}

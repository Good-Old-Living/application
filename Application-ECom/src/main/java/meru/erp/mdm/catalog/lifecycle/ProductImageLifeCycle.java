package meru.erp.mdm.catalog.lifecycle;

import meru.image.lifecycle.EntityImageLifeCycle;
import meru.image.repository.ImageRepository;
import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductImage;

public class ProductImageLifeCycle extends EntityImageLifeCycle<ProductImage> {

  private ProductLifeCycle mProductLifeCycle;

  @Override
  public void init() {
    super.init();
    mProductLifeCycle = appEngine.getEntityLifeCycle(Product.class,
                                                     ProductLifeCycle.class);
  }

  @Override
  public boolean preCreate(ProductImage image) {
    super.preCreate(image);
    mProductLifeCycle.onProductImage(image);
    return false;
  }

  @Override
  public boolean preModify(ProductImage image) {
    super.preModify(image);
    mProductLifeCycle.onProductImage(image);
    return false;
  }

  @Override
  public boolean preDelete(Class<ProductImage> resourceClass,
                           Object id) {
    super.preDelete(resourceClass,
                    id);
    //mProductLifeCycle.onProductImage(image);
    return false;
  }

  
  @Override
  protected String getImgHome() {
      return "product";
  }

  @Override
  protected void configureImageRepository(ImageRepository<ProductImage> imageRepository) {
      imageRepository.requiresBucket(true);
  }
}

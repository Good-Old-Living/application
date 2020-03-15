package meru.erp.mdm.catalog.lifecycle;

import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductImage;
import app.erp.mdm.catalog.ProductLineItem;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.data.format.json.JsonHelper;
import meru.image.ImageSize;
import meru.image.repository.ImageRepository;

public class ProductLifeCycle extends AbstractEntityLifeCycle<Product> {

  private ProductLineItemLifeCycle mProductLineItemLifeCycle;

  @Override
  public void init() {
    super.init();
    mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                             ProductLineItemLifeCycle.class);
  }

  @Override
  public boolean preCreate(Product product) {
    updateProduct(product);

    return true;
  }

  @Override
  public boolean preModify(Product product) {
    updateProduct(product);
    return true;
  }

  private void updateProduct(Product product) {

    String desc = JsonHelper.escape(product.getDescription());
    product.setDescription(desc);

    if (product.getTnImage() == null) {
      product.setTnImage("/img/noimage.gif");
    }
    
  }

  public void onProductImage(ProductImage productImage) {

    Product product = appEngine.get(Product.class,
                                    productImage.getEntityId());
    String image = ImageRepository.getImagePath(productImage.getImage(),
                                 ImageSize.Size150x150);
    product.setTnImage(image);
    appEngine.save(product);

    mProductLineItemLifeCycle.onProductImage(productImage);

  }

}

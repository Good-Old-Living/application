package meru.erp.mdm.catalog.lifecycle;

import java.text.DecimalFormat;
import java.util.List;

import app.domain.EntityFeatureValue;
import app.domain.Property;
import app.erp.finance.Tax;
import app.erp.mdm.catalog.Product;
import app.erp.mdm.catalog.ProductCategory;
import app.erp.mdm.catalog.ProductImage;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.mdm.catalog.ProductLineItemImage;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.erp.mdm.catalog.ProductLineItemPriceProvider;
import meru.erp.mdm.catalog.UOM;
import meru.exception.AppException;
import meru.image.ImageSize;
import meru.image.repository.ImageRepository;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;

public class ProductLineItemLifeCycle extends AbstractEntityLifeCycle<ProductLineItem> {

  public DecimalFormat priceFormat;
  private ProductLineItemPriceProvider productLineItemPriceProvider;

  @Override
  public void init() {

    // String format =
    // appConfig.getMandatoryProperty("app.config.price-format");
    // priceFormat = new DecimalFormat(format);
    /*
     * String priceProvider = appConfig.getProperty("app.ecom.price.provider");
     * if (priceProvider != null) { try { productLineItemPriceProvider =
     * (ProductLineItemPriceProvider) Class.forName(priceProvider)
     * .newInstance(); } catch (Exception e) { throw new RuntimeException(e); }
     * }
     */ }

  @Override
  public Class<?>[] observeableEntities() {
    return new Class<?>[] { Property.class, EntityFeatureValue.class };
  }

  @Override
  public boolean preCreate(ProductLineItem productItem) {

    updateProductLineItem(productItem);
    return true;
  }

  @Override
  public boolean preModify(ProductLineItem productItem) {
    updateProductLineItem(productItem);
    return true;
  }

  @Override
  public void onObserveableEntityCreate(Object entity) {

  }

  @Override
  public void onObserveableEntityModify(Object entity) {

    boolean isTrue = false;

    if (entity instanceof Property) {
      Property property = (Property) entity;
      if (property.getName().equals("price.silver")) {
        isTrue = true;
      }
    } else if (entity instanceof EntityFeatureValue) {
      isTrue = true;
    }

    if (isTrue) {
      EntityQuery<ProductLineItem> entityQuery = appEngine.createQuery(ProductLineItem.class);
      List<ProductLineItem> productLineItems = appEngine.get(entityQuery);
      for (ProductLineItem productLineItem : productLineItems) {
        updateProductLineItem(productLineItem);
      }

    }

  }

  public void onProductImage(ProductImage productImage) {
    updateTnImage(productImage.getEntityId(),
                  ImageRepository.getImagePath(productImage.getImage(),
                                               ImageSize.Size150x150));
  }

  public void onProductLineItemImage(ProductLineItemImage productLineItemImage) {

    updateProductLineItemTnImage(productLineItemImage.getEntityId(),
                                 ImageRepository.getImagePath(productLineItemImage.getImage(),
                                                              ImageSize.Size150x150));
  }

  private void updateTnImage(String productId,
                             String image) {

    Object pId = productId;

    if (productId instanceof String) {
      try {
        pId = Long.parseLong((String) productId);
      } catch (NumberFormatException e) {
        // Ignore. The id might be a string
      }
    }

    EntityQuery<ProductLineItem> entityQuery = appEngine.createQuery(ProductLineItem.class);
    entityQuery.addQueryParameter("product.id",
                                  pId);
    List<ProductLineItem> productLineItems = appEngine.get(entityQuery);

    for (ProductLineItem productLineItem : productLineItems) {
      productLineItem.setTnImage(image);
      appEngine.save(productLineItem);
    }
  }

  private void updateProductLineItemTnImage(String productLineItemId,
                                            String image) {

    Object pId = productLineItemId;

    if (productLineItemId instanceof String) {
      try {
        pId = Long.parseLong((String) productLineItemId);
      } catch (NumberFormatException e) {
        // Ignore. The id might be a string
      }
    }

    ProductLineItem productLineItem = appEngine.get(ProductLineItem.class,
                                                    pId);

    if (productLineItem != null) {

      productLineItem.setTnImage(image);
      appEngine.save(productLineItem);
    }
  }

  private void updateProductLineItem(ProductLineItem productItem) {
    Product product = appEngine.get(Product.class,
                                    productItem.getProduct().getId());
    productItem.setProduct(product);

    String tnImage = productItem.getTnImage();
    if (tnImage == null || tnImage.equals("null")) {
      productItem.setTnImage(product.getTnImage());
    }

    Float discount = productItem.getDiscount();

    float mrp = 0;
    if (productLineItemPriceProvider == null) {
      mrp = productItem.getMrp();
    } else {
      mrp = productLineItemPriceProvider.getPrice(productItem,
                                                  getTax(productItem),
                                                  appEngine);
      productItem.setMrp(mrp);
    }

    if (discount == null) {
      productItem.setPrice(mrp);
      productItem.setSavings(null);
    } else {
      float price = 0;
      if (productItem.getDiscountType() == null) {
        price = mrp - discount;
      } else {
        price = mrp - (mrp * (discount / 100));
      }
      price = Float.valueOf((float) Math.floor(price));
      productItem.setPrice(price);
      float savings = mrp - price;
      savings = Float.valueOf((float) Math.round(savings));
      productItem.setSavings(savings);
    }

    productItem.setSearchTerms("temp");

    if (productItem.getTnImage() == null) {
      productItem.setTnImage(product.getTnImage());
    }

    // productItem.setSearchTerms(ProductIndexBuilder.getSearchTerm(productItem));
    updateSiblingProductItemsPrice(productItem);
  }

  public void updateSiblingProductItemsPrice(ProductLineItem productLineItem) {

    if (isVegOrFruits(productLineItem) && productLineItem.getQuantity() == 1
        && UOM.getUnitOfMessure(productLineItem.getUnitOfMeasure().getValue()) == UOM.KG) {

      Float mrp = productLineItem.getMrp();
      EntityQuery<ProductLineItem> entityQuery = appEngine.createQuery(ProductLineItem.class);
      entityQuery.addQueryParameter("product.id",
                                    productLineItem.getProduct().getId());
      entityQuery.addQueryParameter("id",
                                    AttributeOperator.NOT_EQUALS,
                                    productLineItem.getId());

      List<ProductLineItem> productLineItems = appEngine.get(entityQuery);

      for (ProductLineItem plItem : productLineItems) {
        UOM uom = UOM.getUnitOfMessure(plItem.getUnitOfMeasure().getValue());
        float price = uom.adjustPrice(plItem,
                                      mrp);
        plItem.setMrp(price);
        appEngine.save(plItem);
      }
    }
  }

  private static boolean isVegOrFruits(ProductLineItem productLineItem) {
    long categoryId = productLineItem.getProduct().getProductCategory().getId();

    return categoryId == 101 || categoryId == 102;
  }

  public Tax getTax(ProductLineItem productItem) {

    Tax tax = productItem.getProduct().getTax();

    if (tax == null) {

      ProductCategory category = productItem.getProduct().getProductCategory();
      tax = category.getTax();

      while (tax == null) {

        if (category.getParentId() == null) {
          break;
        }

        category = appEngine.get(ProductCategory.class,
                                 category.getParentId());
        tax = category.getTax();
      }

    }

    return tax;
  }

  public boolean checkAvailableQuantity(ProductLineItem prodLineItem,
                                        int quantity) {

    Integer netQuantity = prodLineItem.getNetQuantity();
    if (netQuantity == null || netQuantity == -1) {
      return false;
    }

    int netQty = netQuantity;
    if (netQty == 0) {
      throw new AppException("The item '" + prodLineItem.getProduct().getName() + "' is out of stock");
    }

    if (quantity > netQty) {
      throw new AppException("The requested quantity of '" + prodLineItem.getProduct().getName()
          + " exceeds the available quantity [" + prodLineItem.getNetQuantity() + "]");
    }

    return true;

  }

  public synchronized void reduceAvailableQuantity(ProductLineItem prodLineItem,
                                                   int quantity) {
    if (checkAvailableQuantity(prodLineItem,
                               quantity)) {
      prodLineItem.setNetQuantity(prodLineItem.getNetQuantity() - quantity);

      appEngine.save(prodLineItem);
    }

  }

}

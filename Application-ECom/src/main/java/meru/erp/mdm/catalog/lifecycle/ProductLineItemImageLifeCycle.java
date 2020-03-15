package meru.erp.mdm.catalog.lifecycle;

import java.util.List;

import meru.image.ImageSize;
import meru.image.lifecycle.EntityImageLifeCycle;
import meru.image.repository.ImageRepository;
import meru.persistence.EntityQuery;
import app.erp.mdm.catalog.ProductLineItem;
import app.erp.mdm.catalog.ProductLineItemImage;

public class ProductLineItemImageLifeCycle
        extends EntityImageLifeCycle<ProductLineItemImage> {

    private ProductLineItemLifeCycle mProductLineItemLifeCycle;

    @Override
    public void init() {
        super.init();
        mProductLineItemLifeCycle = appEngine.getEntityLifeCycle(ProductLineItem.class,
                                                                 ProductLineItemLifeCycle.class);
    }

    @Override
    protected String getImgHome() {
        return "product";
    }

    private String getRelativePath(String productId) {
        return getRelativePath(productId,
                               false);
    }

    private String getRelativePath(String productId, boolean isProductHome) {
        long bucket = ImageRepository.getImageBucket(productId);

        if (isProductHome) {
            return String.valueOf(bucket);
        }

        return bucket + "/" + productId;
    }

    @Override
    public boolean preCreate(ProductLineItemImage image) {
        image.setRelativePath(getRelativePath(image.getProductId()));
        imageRepository.storeImage(image);
        mProductLineItemLifeCycle.onProductLineItemImage(image);
        return false;
    }

    @Override
    public boolean preModify(ProductLineItemImage image) {
        image.setRelativePath(getRelativePath(image.getProductId()));
        imageRepository.storeImage(image);
        mProductLineItemLifeCycle.onProductLineItemImage(image);
        return false;
    }

    @Override
    public boolean preDelete(Class<ProductLineItemImage> resourceClass,
                             Object id) {
        imageRepository.deleteImage((String) id);
        return false;
    }

    @Override
    public List<ProductLineItemImage> preGet(EntityQuery<ProductLineItemImage> entityQuery) {
        String productId = (String) entityQuery.getQueryParameterValue("productId");
        String entityId = (String) entityQuery.getQueryParameterValue("entityId");

        ImageSize imageSize = null;

        String size = (String) entityQuery.getQueryParameterValue("size");

        if (size != null) {
            imageSize = ImageSize.getImageSize(size);
        }

        List<ProductLineItemImage> images = imageRepository.getEntityImages(getRelativePath(productId),
                                                                            entityId,
                                                                            imageSize);

        if (images.isEmpty()) {
            images = imageRepository.getEntityImages(getRelativePath(productId,
                                                                     true),
                                                     entityId,
                                                     imageSize);
        }

        return images;
    }

    protected String getRelativePath(EntityQuery<ProductLineItemImage> entityQuery) {
        return null;
    }

    @Override
    public ProductLineItemImage preGet(Class<ProductLineItemImage> entityClass,
                                       Object id) {

        String idStr = (String) id;

        idStr = idStr.substring(0,
                                idStr.lastIndexOf('/'));
        idStr = idStr.substring(0,
                                idStr.lastIndexOf('/'));
        idStr = idStr.substring(idStr.lastIndexOf('/') + 1);

        ProductLineItemImage image = imageRepository.getEntityImage((String) id);
        image.setProductId(idStr);
        return image;
    }

}

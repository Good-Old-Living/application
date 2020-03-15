package meru.erp.mdm.catalog;

import app.domain.image.EntityImage;

public class ProductLineItemImage1 extends EntityImage {

    private long productId;

    public ProductLineItemImage1() {
        super();
    }

    public ProductLineItemImage1(String id,
                                long productId,
                                long entityId,
                                String image) {
//        super(id,
//              entityId,
//              image);
        this.productId = productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

}

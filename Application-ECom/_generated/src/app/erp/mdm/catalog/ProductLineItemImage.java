package app.erp.mdm.catalog;

import app.domain.image.EntityImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mdm_product_line_item_image")
public class ProductLineItemImage extends EntityImage {


    @Column(name="product_id", nullable=false)
    private java.lang.String productId;

    public java.lang.String getProductId() {
        
        return productId;
    }

    public void setProductId(java.lang.String productId) {

        this.productId = productId;
    }
}

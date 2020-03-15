package app.erp.mdm.catalog;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="mdm_product_feature")
public class ProductFeature extends AppEntity {


    @Column(name="product_id", nullable=false)
    private java.lang.String productId;

    @Column(name="product_category_feature_id")
    private java.lang.String productCategoryFeatureId;

    @Column(name="name", nullable=false)
    private java.lang.String name;

    @OneToOne
    @JoinColumn(name="type_id", nullable=false)
    private app.domain.PropertyGroup type;

    @Column(name="attributes")
    private java.lang.String attributes;

    @Column(name="sort_order", nullable=false)
    private Integer sortOrder;

    public java.lang.String getProductId() {
        
        return productId;
    }

    public void setProductId(java.lang.String productId) {

        this.productId = productId;
    }

    public java.lang.String getProductCategoryFeatureId() {
        
        return productCategoryFeatureId;
    }

    public void setProductCategoryFeatureId(java.lang.String productCategoryFeatureId) {

        this.productCategoryFeatureId = productCategoryFeatureId;
    }

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public app.domain.PropertyGroup getType() {
        
        return type;
    }

    public void setType(app.domain.PropertyGroup type) {

        this.type = type;
    }

    public java.lang.String getAttributes() {
        
        return attributes;
    }

    public void setAttributes(java.lang.String attributes) {

        this.attributes = attributes;
    }

    public Integer getSortOrder() {
        
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {

        this.sortOrder = sortOrder;
    }

    public String toString() {
        return name;
    }
}

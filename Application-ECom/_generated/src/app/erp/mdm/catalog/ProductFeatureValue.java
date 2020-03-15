package app.erp.mdm.catalog;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mdm_product_feature_value")
public class ProductFeatureValue extends AppEntity {


    @Column(name="owner_id", nullable=false)
    private java.lang.String ownerId;

    @Column(name="owner_type", nullable=false)
    private java.lang.String ownerType;

    @Column(name="row_attribute_name")
    private java.lang.String rowAttributeName;

    @Column(name="column_attribute_name")
    private java.lang.String columnAttributeName;

    @Column(name="value")
    private java.lang.String value;

    @Column(name="sort_order")
    private Integer sortOrder;

    public java.lang.String getOwnerId() {
        
        return ownerId;
    }

    public void setOwnerId(java.lang.String ownerId) {

        this.ownerId = ownerId;
    }

    public java.lang.String getOwnerType() {
        
        return ownerType;
    }

    public void setOwnerType(java.lang.String ownerType) {

        this.ownerType = ownerType;
    }

    public java.lang.String getRowAttributeName() {
        
        return rowAttributeName;
    }

    public void setRowAttributeName(java.lang.String rowAttributeName) {

        this.rowAttributeName = rowAttributeName;
    }

    public java.lang.String getColumnAttributeName() {
        
        return columnAttributeName;
    }

    public void setColumnAttributeName(java.lang.String columnAttributeName) {

        this.columnAttributeName = columnAttributeName;
    }

    public java.lang.String getValue() {
        
        return value;
    }

    public void setValue(java.lang.String value) {

        this.value = value;
    }

    public Integer getSortOrder() {
        
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {

        this.sortOrder = sortOrder;
    }
}

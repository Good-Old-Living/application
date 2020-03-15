package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_entity_feature_value")
public class EntityFeatureValue extends AppEntity {


    @Column(name="entity_feature_id", nullable=false)
    private Long entityFeatureId;

    @Column(name="row_attribute_name")
    private java.lang.String rowAttributeName;

    @Column(name="column_attribute_name")
    private java.lang.String columnAttributeName;

    @Column(name="value")
    private java.lang.String value;

    @Column(name="sort_order")
    private Integer sortOrder;

    public Long getEntityFeatureId() {
        
        return entityFeatureId;
    }

    public void setEntityFeatureId(Long entityFeatureId) {

        this.entityFeatureId = entityFeatureId;
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

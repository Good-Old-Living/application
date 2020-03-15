package app.domain;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="core_entity_feature")
public class EntityFeature extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @OneToOne
    @JoinColumn(name="type_id", nullable=false)
    private app.domain.PropertyGroup type;

    @Column(name="owner_id", nullable=false)
    private Long ownerId;

    @Column(name="owner_type", nullable=false)
    private java.lang.String ownerType;

    @Column(name="sort_order")
    private Integer sortOrder;

    @OneToMany(mappedBy="entityFeatureId", cascade={CascadeType.ALL})
    private java.util.List<app.domain.EntityFeatureValue> values;

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

    public Long getOwnerId() {
        
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {

        this.ownerId = ownerId;
    }

    public java.lang.String getOwnerType() {
        
        return ownerType;
    }

    public void setOwnerType(java.lang.String ownerType) {

        this.ownerType = ownerType;
    }

    public Integer getSortOrder() {
        
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {

        this.sortOrder = sortOrder;
    }

    public java.util.List<app.domain.EntityFeatureValue> getValues() {

        if (values == null) {
            this.values = new java.util.ArrayList<app.domain.EntityFeatureValue>();
        }
        
        return values;
    }

    public void setValues(java.util.List<app.domain.EntityFeatureValue> values) {

        this.values = values;
    }

    public String toString() {
        return name;
    }
}

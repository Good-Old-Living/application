package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_property")
public class Property extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="type", nullable=false)
    private java.lang.String type;

    @Column(name="owner_id")
    private Long ownerId;

    @Column(name="category")
    private java.lang.String category;

    @Column(name="prefix")
    private java.lang.String prefix;

    @Column(name="value", nullable=false)
    private java.lang.String value;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getType() {
        
        return type;
    }

    public void setType(java.lang.String type) {

        this.type = type;
    }

    public Long getOwnerId() {
        
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {

        this.ownerId = ownerId;
    }

    public java.lang.String getCategory() {
        
        return category;
    }

    public void setCategory(java.lang.String category) {

        this.category = category;
    }

    public java.lang.String getPrefix() {
        
        return prefix;
    }

    public void setPrefix(java.lang.String prefix) {

        this.prefix = prefix;
    }

    public java.lang.String getValue() {
        
        return value;
    }

    public void setValue(java.lang.String value) {

        this.value = value;
    }

    public String toString() {
        return name;
    }
}

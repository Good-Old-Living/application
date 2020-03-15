package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_property_group")
public class PropertyGroup extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="value", nullable=false)
    private java.lang.String value;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
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

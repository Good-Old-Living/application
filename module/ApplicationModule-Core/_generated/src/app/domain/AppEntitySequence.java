package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_app_entity_sequence")
public class AppEntitySequence extends AppEntity implements app.entity.Nameable {


    @Column(name="entity_id")
    private Long entityId;

    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="value", nullable=false)
    private Long value;

    public Long getEntityId() {
        
        return entityId;
    }

    public void setEntityId(Long entityId) {

        this.entityId = entityId;
    }

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public Long getValue() {
        
        return value;
    }

    public void setValue(Long value) {

        this.value = value;
    }

    public String toString() {
        return name;
    }
}

package app.domain;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_sequence_id")
public class SequenceId {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="entity_id", nullable=false)
    private Long entityId;

    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="value", nullable=false)
    private Long value;

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

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

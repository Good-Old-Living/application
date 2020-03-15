package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_app_entity_state")
public class AppEntityState extends AppEntity {


    @Column(name="entity", nullable=false)
    private java.lang.String entity;

    @Column(name="code", nullable=false)
    private Integer code;

    @Column(name="status", nullable=false)
    private java.lang.String status;

    public java.lang.String getEntity() {
        
        return entity;
    }

    public void setEntity(java.lang.String entity) {

        this.entity = entity;
    }

    public Integer getCode() {
        
        return code;
    }

    public void setCode(Integer code) {

        this.code = code;
    }

    public java.lang.String getStatus() {
        
        return status;
    }

    public void setStatus(java.lang.String status) {

        this.status = status;
    }
}

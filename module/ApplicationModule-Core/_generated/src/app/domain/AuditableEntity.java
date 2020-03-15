package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AuditableEntity extends AppEntity {


    @Column(name="created_by", nullable=false)
    private java.lang.String createdBy;

    @Column(name="created_time", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdTime;

    @Column(name="modified_by")
    private java.lang.String modifiedBy;

    @Column(name="modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar modifiedTime;

    public java.lang.String getCreatedBy() {
        
        return createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {

        this.createdBy = createdBy;
    }

    public java.util.Calendar getCreatedTime() {
        
        return createdTime;
    }

    public void setCreatedTime(java.util.Calendar createdTime) {

        this.createdTime = createdTime;
    }

    public java.lang.String getModifiedBy() {
        
        return modifiedBy;
    }

    public void setModifiedBy(java.lang.String modifiedBy) {

        this.modifiedBy = modifiedBy;
    }

    public java.util.Calendar getModifiedTime() {
        
        return modifiedTime;
    }

    public void setModifiedTime(java.util.Calendar modifiedTime) {

        this.modifiedTime = modifiedTime;
    }
}

package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bp_customer_wallet")
public class CustomerWallet extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="amount", nullable=false)
    private Integer amount;

    @Column(name="created_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdOn;

    @Column(name="updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar updatedOn;

    public Long getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public Integer getAmount() {
        
        return amount;
    }

    public void setAmount(Integer amount) {

        this.amount = amount;
    }

    public java.util.Calendar getCreatedOn() {
        
        return createdOn;
    }

    public void setCreatedOn(java.util.Calendar createdOn) {

        this.createdOn = createdOn;
    }

    public java.util.Calendar getUpdatedOn() {
        
        return updatedOn;
    }

    public void setUpdatedOn(java.util.Calendar updatedOn) {

        this.updatedOn = updatedOn;
    }
}

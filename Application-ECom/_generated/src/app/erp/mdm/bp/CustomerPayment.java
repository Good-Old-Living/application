package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bp_customer_payment")
public class CustomerPayment extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="transaction_id", nullable=false)
    private java.lang.String transactionId;

    @Column(name="amount", nullable=false)
    private Integer amount;

    @Column(name="status", nullable=false)
    private java.lang.String status;

    @Column(name="checksum", nullable=false)
    private java.lang.String checksum;

    @Column(name="created_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdOn;

    public Long getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public java.lang.String getTransactionId() {
        
        return transactionId;
    }

    public void setTransactionId(java.lang.String transactionId) {

        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        
        return amount;
    }

    public void setAmount(Integer amount) {

        this.amount = amount;
    }

    public java.lang.String getStatus() {
        
        return status;
    }

    public void setStatus(java.lang.String status) {

        this.status = status;
    }

    public java.lang.String getChecksum() {
        
        return checksum;
    }

    public void setChecksum(java.lang.String checksum) {

        this.checksum = checksum;
    }

    public java.util.Calendar getCreatedOn() {
        
        return createdOn;
    }

    public void setCreatedOn(java.util.Calendar createdOn) {

        this.createdOn = createdOn;
    }
}

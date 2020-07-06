package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bp_customer_wallet_history")
public class CustomerWalletHistory extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="curr_amount", nullable=false)
    private Integer currAmount;

    @Column(name="prev_amount", nullable=false)
    private Integer prevAmount;

    @Column(name="amount_deducted")
    private Integer amountDeducted;

    @Column(name="amount_added")
    private Integer amountAdded;

    @Column(name="description")
    private java.lang.String description;

    @Column(name="created_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdOn;

    public Long getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public Integer getCurrAmount() {
        
        return currAmount;
    }

    public void setCurrAmount(Integer currAmount) {

        this.currAmount = currAmount;
    }

    public Integer getPrevAmount() {
        
        return prevAmount;
    }

    public void setPrevAmount(Integer prevAmount) {

        this.prevAmount = prevAmount;
    }

    public Integer getAmountDeducted() {
        
        return amountDeducted;
    }

    public void setAmountDeducted(Integer amountDeducted) {

        this.amountDeducted = amountDeducted;
    }

    public Integer getAmountAdded() {
        
        return amountAdded;
    }

    public void setAmountAdded(Integer amountAdded) {

        this.amountAdded = amountAdded;
    }

    public java.lang.String getDescription() {
        
        return description;
    }

    public void setDescription(java.lang.String description) {

        this.description = description;
    }

    public java.util.Calendar getCreatedOn() {
        
        return createdOn;
    }

    public void setCreatedOn(java.util.Calendar createdOn) {

        this.createdOn = createdOn;
    }
}

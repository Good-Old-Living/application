package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bp_customer_wallet_amount")
public class CustomerWalletAmount extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="amount", nullable=false)
    private Integer amount;

    @Column(name="description")
    private java.lang.String description;

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

    public java.lang.String getDescription() {
        
        return description;
    }

    public void setDescription(java.lang.String description) {

        this.description = description;
    }
}

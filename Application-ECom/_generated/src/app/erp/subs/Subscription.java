package app.erp.subs;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="subs_subscription")
public class Subscription extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @OneToOne
    @JoinColumn(name="delivery_address_id", nullable=false)
    private app.erp.mdm.bp.CustomerAddress deliveryAddress;

    @OneToOne
    @JoinColumn(name="state_id", nullable=false)
    private app.domain.AppEntityState state;

    @OneToMany(mappedBy="subscriptionId", cascade={CascadeType.ALL})
    private java.util.List<app.erp.subs.SubscriptionLineItem> lineItems;

    public Long getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public app.erp.mdm.bp.CustomerAddress getDeliveryAddress() {
        
        return deliveryAddress;
    }

    public void setDeliveryAddress(app.erp.mdm.bp.CustomerAddress deliveryAddress) {

        this.deliveryAddress = deliveryAddress;
    }

    public app.domain.AppEntityState getState() {
        
        return state;
    }

    public void setState(app.domain.AppEntityState state) {

        this.state = state;
    }

    public java.util.List<app.erp.subs.SubscriptionLineItem> getLineItems() {

        if (lineItems == null) {
            this.lineItems = new java.util.ArrayList<app.erp.subs.SubscriptionLineItem>();
        }
        
        return lineItems;
    }

    public void setLineItems(java.util.List<app.erp.subs.SubscriptionLineItem> lineItems) {

        this.lineItems = lineItems;
    }
}

package app.erp.subs;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="subs_subscription_line_item")
public class SubscriptionLineItem extends AppEntity {


    @Column(name="subscription_id", nullable=false)
    private Long subscriptionId;

    @OneToOne
    @JoinColumn(name="product_line_item_id", nullable=false)
    private app.erp.mdm.catalog.ProductLineItem productLineItem;

    @Column(name="quantity", nullable=false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(name="frequency_id", nullable=false)
    private app.domain.PropertyGroup frequency;

    @OneToMany(mappedBy="subscriptionLineItemId", cascade={CascadeType.ALL})
    private java.util.List<app.erp.subs.SubscriptionDeviation> deviations;

    @Column(name="start_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDate;

    @OneToOne
    @JoinColumn(name="state_id", nullable=false)
    private app.domain.AppEntityState state;

    public Long getSubscriptionId() {
        
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {

        this.subscriptionId = subscriptionId;
    }

    public app.erp.mdm.catalog.ProductLineItem getProductLineItem() {
        
        return productLineItem;
    }

    public void setProductLineItem(app.erp.mdm.catalog.ProductLineItem productLineItem) {

        this.productLineItem = productLineItem;
    }

    public Integer getQuantity() {
        
        return quantity;
    }

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;
    }

    public app.domain.PropertyGroup getFrequency() {
        
        return frequency;
    }

    public void setFrequency(app.domain.PropertyGroup frequency) {

        this.frequency = frequency;
    }

    public java.util.List<app.erp.subs.SubscriptionDeviation> getDeviations() {

        if (deviations == null) {
            this.deviations = new java.util.ArrayList<app.erp.subs.SubscriptionDeviation>();
        }
        
        return deviations;
    }

    public void setDeviations(java.util.List<app.erp.subs.SubscriptionDeviation> deviations) {

        this.deviations = deviations;
    }

    public java.util.Date getStartDate() {
        
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {

        this.startDate = startDate;
    }

    public app.domain.AppEntityState getState() {
        
        return state;
    }

    public void setState(app.domain.AppEntityState state) {

        this.state = state;
    }
}

package app.erp.subs;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="subs_subscription_deviation")
public class SubscriptionDeviation extends AppEntity {


    @Column(name="subscription_line_item_id")
    private Long subscriptionLineItemId;

    @Column(name="quantity", nullable=false)
    private Integer quantity;

    @Column(name="start_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDate;

    @Column(name="end_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endDate;

    public Long getSubscriptionLineItemId() {
        
        return subscriptionLineItemId;
    }

    public void setSubscriptionLineItemId(Long subscriptionLineItemId) {

        this.subscriptionLineItemId = subscriptionLineItemId;
    }

    public Integer getQuantity() {
        
        return quantity;
    }

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;
    }

    public java.util.Date getStartDate() {
        
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {

        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {

        this.endDate = endDate;
    }
}

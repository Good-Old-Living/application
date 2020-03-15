package app.erp.purchase;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="purs_purchase_order")
public class PurchaseOrder extends AppEntity {


    @Column(name="order_id")
    private java.lang.String orderId;

    @OneToOne
    @JoinColumn(name="business_partner_id", nullable=false)
    private app.erp.mdm.bp.BusinessPartner businessPartner;

    @Column(name="placed_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar placedOn;

    @OneToOne
    @JoinColumn(name="state_id", nullable=false)
    private app.domain.AppEntityState state;

    @Column(name="amount", nullable=false)
    private Float amount;

    public java.lang.String getOrderId() {
        
        return orderId;
    }

    public void setOrderId(java.lang.String orderId) {

        this.orderId = orderId;
    }

    public app.erp.mdm.bp.BusinessPartner getBusinessPartner() {
        
        return businessPartner;
    }

    public void setBusinessPartner(app.erp.mdm.bp.BusinessPartner businessPartner) {

        this.businessPartner = businessPartner;
    }

    public java.util.Calendar getPlacedOn() {
        
        return placedOn;
    }

    public void setPlacedOn(java.util.Calendar placedOn) {

        this.placedOn = placedOn;
    }

    public app.domain.AppEntityState getState() {
        
        return state;
    }

    public void setState(app.domain.AppEntityState state) {

        this.state = state;
    }

    public Float getAmount() {
        
        return amount;
    }

    public void setAmount(Float amount) {

        this.amount = amount;
    }
}

package app.erp.purchase;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="purs_purchase_order_line_item")
public class PurchaseOrderLineItem extends AppEntity {


    @Column(name="purchase_order_id", nullable=false)
    private java.lang.String purchaseOrderId;

    @Column(name="quantity", nullable=false)
    private Integer quantity;

    @Column(name="net_quantity")
    private Float netQuantity;

    @Column(name="total_price", nullable=false)
    private Float totalPrice;

    @Column(name="unit_mrp", nullable=false)
    private Float unitMrp;

    @Column(name="unit_price", nullable=false)
    private Float unitPrice;

    @Column(name="discount")
    private Float discount;

    @OneToOne
    @JoinColumn(name="discount_type_id")
    private app.domain.PropertyGroup discountType;

    @Column(name="tax_rate", nullable=false)
    private Float taxRate;

    @Column(name="notes")
    private java.lang.String notes;

    public java.lang.String getPurchaseOrderId() {
        
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(java.lang.String purchaseOrderId) {

        this.purchaseOrderId = purchaseOrderId;
    }

    public Integer getQuantity() {
        
        return quantity;
    }

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;
    }

    public Float getNetQuantity() {
        
        return netQuantity;
    }

    public void setNetQuantity(Float netQuantity) {

        this.netQuantity = netQuantity;
    }

    public Float getTotalPrice() {
        
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {

        this.totalPrice = totalPrice;
    }

    public Float getUnitMrp() {
        
        return unitMrp;
    }

    public void setUnitMrp(Float unitMrp) {

        this.unitMrp = unitMrp;
    }

    public Float getUnitPrice() {
        
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {

        this.unitPrice = unitPrice;
    }

    public Float getDiscount() {
        
        return discount;
    }

    public void setDiscount(Float discount) {

        this.discount = discount;
    }

    public app.domain.PropertyGroup getDiscountType() {
        
        return discountType;
    }

    public void setDiscountType(app.domain.PropertyGroup discountType) {

        this.discountType = discountType;
    }

    public Float getTaxRate() {
        
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {

        this.taxRate = taxRate;
    }

    public java.lang.String getNotes() {
        
        return notes;
    }

    public void setNotes(java.lang.String notes) {

        this.notes = notes;
    }
}

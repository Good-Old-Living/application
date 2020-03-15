package app.erp.marketing;

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
@Table(name="mar_sales_offer")
public class SalesOffer extends AppEntity {


    @Column(name="title", nullable=false)
    private java.lang.String title;

    @Column(name="start_time", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startTime;

    @Column(name="end_time", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endTime;

    @OneToOne
    @JoinColumn(name="product_category_id")
    private app.erp.mdm.catalog.ProductCategory productCategory;

    @Column(name="minimum_purchase")
    private Long minimumPurchase;

    @Column(name="customer_types")
    private java.lang.String customerTypes;

    @Column(name="discount", nullable=false)
    private Float discount;

    @OneToOne
    @JoinColumn(name="discount_type_id", nullable=false)
    private app.domain.PropertyGroup discountType;

    @Column(name="sort_order")
    private Integer sortOrder;

    @Column(name="is_complimentary_or")
    private String isComplimentaryOr = "N";
    private transient boolean isComplimentaryOrBoolean;

    @OneToMany(mappedBy="salesOfferId", cascade={CascadeType.ALL})
    private java.util.List<app.erp.marketing.SalesOrderComplimentaryItem> complimentaryItems;

    public java.lang.String getTitle() {
        
        return title;
    }

    public void setTitle(java.lang.String title) {

        this.title = title;
    }

    public java.util.Date getStartTime() {
        
        return startTime;
    }

    public void setStartTime(java.util.Date startTime) {

        this.startTime = startTime;
    }

    public java.util.Date getEndTime() {
        
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {

        this.endTime = endTime;
    }

    public app.erp.mdm.catalog.ProductCategory getProductCategory() {
        
        return productCategory;
    }

    public void setProductCategory(app.erp.mdm.catalog.ProductCategory productCategory) {

        this.productCategory = productCategory;
    }

    public Long getMinimumPurchase() {
        
        return minimumPurchase;
    }

    public void setMinimumPurchase(Long minimumPurchase) {

        this.minimumPurchase = minimumPurchase;
    }

    public java.lang.String getCustomerTypes() {
        
        return customerTypes;
    }

    public void setCustomerTypes(java.lang.String customerTypes) {

        this.customerTypes = customerTypes;
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

    public Integer getSortOrder() {
        
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {

        this.sortOrder = sortOrder;
    }

    public java.lang.String getIsComplimentaryOr() {
        
        return isComplimentaryOr;
    }

    public void setIsComplimentaryOr(java.lang.String isComplimentaryOr) {

        this.isComplimentaryOr = isComplimentaryOr;
    }

    public boolean isComplimentaryOr() {

        return "Y".equals(isComplimentaryOr);
    }

    public Boolean getIsComplimentaryOrBoolean() {
        
        return isComplimentaryOr != null && isComplimentaryOr.equals("Y");
    }

    public void setIsComplimentaryOrBoolean(Boolean isComplimentaryOrBoolean) {

        isComplimentaryOr = isComplimentaryOrBoolean ? "Y" : "N";
    }

    public boolean isComplimentaryOrBoolean() {

        return "Y".equals(isComplimentaryOrBoolean);
    }

    public java.util.List<app.erp.marketing.SalesOrderComplimentaryItem> getComplimentaryItems() {

        if (complimentaryItems == null) {
            this.complimentaryItems = new java.util.ArrayList<app.erp.marketing.SalesOrderComplimentaryItem>();
        }
        
        return complimentaryItems;
    }

    public void setComplimentaryItems(java.util.List<app.erp.marketing.SalesOrderComplimentaryItem> complimentaryItems) {

        this.complimentaryItems = complimentaryItems;
    }
}

package app.erp.marketing;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="mar_sales_order_complimentary_item")
public class SalesOrderComplimentaryItem extends AppEntity {


    @Column(name="sales_offer_id", nullable=false)
    private java.lang.String salesOfferId;

    @OneToOne
    @JoinColumn(name="product_line_item_id", nullable=false)
    private app.erp.mdm.catalog.ProductLineItem productLineItem;

    @Column(name="mrp", nullable=false)
    private Float mrp;

    public java.lang.String getSalesOfferId() {
        
        return salesOfferId;
    }

    public void setSalesOfferId(java.lang.String salesOfferId) {

        this.salesOfferId = salesOfferId;
    }

    public app.erp.mdm.catalog.ProductLineItem getProductLineItem() {
        
        return productLineItem;
    }

    public void setProductLineItem(app.erp.mdm.catalog.ProductLineItem productLineItem) {

        this.productLineItem = productLineItem;
    }

    public Float getMrp() {
        
        return mrp;
    }

    public void setMrp(Float mrp) {

        this.mrp = mrp;
    }
}

package app.ecom.shopping;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="store_product_notification")
public class ProductNotification extends AppEntity {


    @OneToOne
    @JoinColumn(name="customer_id", nullable=false)
    private app.erp.mdm.bp.Customer customer;

    @OneToOne
    @JoinColumn(name="product_line_item_id", nullable=false)
    private app.erp.mdm.catalog.ProductLineItem productLineItem;

    public app.erp.mdm.bp.Customer getCustomer() {
        
        return customer;
    }

    public void setCustomer(app.erp.mdm.bp.Customer customer) {

        this.customer = customer;
    }

    public app.erp.mdm.catalog.ProductLineItem getProductLineItem() {
        
        return productLineItem;
    }

    public void setProductLineItem(app.erp.mdm.catalog.ProductLineItem productLineItem) {

        this.productLineItem = productLineItem;
    }
}

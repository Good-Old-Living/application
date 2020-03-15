package app.ecom.shopping.cart;

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
@Table(name="store_shopping_cart")
public class ShoppingCart extends AppEntity {


    @OneToOne
    @JoinColumn(name="customer_id")
    private app.erp.mdm.bp.Customer customer;

    @Column(name="session_id", nullable=false)
    private java.lang.String sessionId;

    @Column(name="created_time", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdTime;

    @OneToMany(mappedBy="shoppingCartId", cascade={CascadeType.ALL})
    private java.util.List<app.ecom.shopping.cart.ShoppingCartLineItem> items;

    public app.erp.mdm.bp.Customer getCustomer() {
        
        return customer;
    }

    public void setCustomer(app.erp.mdm.bp.Customer customer) {

        this.customer = customer;
    }

    public java.lang.String getSessionId() {
        
        return sessionId;
    }

    public void setSessionId(java.lang.String sessionId) {

        this.sessionId = sessionId;
    }

    public java.util.Calendar getCreatedTime() {
        
        return createdTime;
    }

    public void setCreatedTime(java.util.Calendar createdTime) {

        this.createdTime = createdTime;
    }

    public java.util.List<app.ecom.shopping.cart.ShoppingCartLineItem> getItems() {

        if (items == null) {
            this.items = new java.util.ArrayList<app.ecom.shopping.cart.ShoppingCartLineItem>();
        }
        
        return items;
    }

    public void setItems(java.util.List<app.ecom.shopping.cart.ShoppingCartLineItem> items) {

        this.items = items;
    }
}

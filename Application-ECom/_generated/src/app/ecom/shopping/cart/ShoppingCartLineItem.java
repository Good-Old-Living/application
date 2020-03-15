package app.ecom.shopping.cart;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="store_shopping_cart_line_item")
public class ShoppingCartLineItem extends AppEntity {


    @Column(name="shopping_cart_id", nullable=false)
    private Long shoppingCartId;

    @OneToOne
    @JoinColumn(name="product_line_item_id", nullable=false)
    private app.erp.mdm.catalog.ProductLineItem productLineItem;

    @Column(name="quantity", nullable=false)
    private Integer quantity;

    @Column(name="total_price", nullable=false)
    private Float totalPrice;

    public Long getShoppingCartId() {
        
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {

        this.shoppingCartId = shoppingCartId;
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

    public Float getTotalPrice() {
        
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {

        this.totalPrice = totalPrice;
    }
}

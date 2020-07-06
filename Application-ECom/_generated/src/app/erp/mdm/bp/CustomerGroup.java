package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bp_customer_group")
public class CustomerGroup extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="discount", nullable=false)
    private Float discount;

    @Column(name="wallet_exempted")
    private String walletExempted = "N";
    private transient boolean walletExemptedBoolean;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public Float getDiscount() {
        
        return discount;
    }

    public void setDiscount(Float discount) {

        this.discount = discount;
    }

    public java.lang.String getWalletExempted() {
        
        return walletExempted;
    }

    public void setWalletExempted(java.lang.String walletExempted) {

        this.walletExempted = walletExempted;
    }

    public boolean walletExempted() {

        return "Y".equals(walletExempted);
    }

    public Boolean getWalletExemptedBoolean() {
        
        return walletExempted != null && walletExempted.equals("Y");
    }

    public void setWalletExemptedBoolean(Boolean walletExemptedBoolean) {

        walletExempted = walletExemptedBoolean ? "Y" : "N";
    }

    public boolean walletExemptedBoolean() {

        return "Y".equals(walletExemptedBoolean);
    }

    public String toString() {
        return name;
    }
}

package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bp_business_partner_group")
public class BusinessPartnerGroup extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="discount", nullable=false)
    private Float discount;

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

    public String toString() {
        return name;
    }
}

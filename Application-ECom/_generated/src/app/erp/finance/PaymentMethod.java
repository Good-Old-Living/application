package app.erp.finance;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="fin_payment_method")
public class PaymentMethod extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="uid", nullable=false)
    private java.lang.String uid;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getUid() {
        
        return uid;
    }

    public void setUid(java.lang.String uid) {

        this.uid = uid;
    }

    public String toString() {
        return name;
    }
}

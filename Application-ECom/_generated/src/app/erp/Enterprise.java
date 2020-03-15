package app.erp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bus_enterprise")
public class Enterprise extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="email", nullable=false)
    private java.lang.String email;

    @Column(name="phone", nullable=false)
    private java.lang.String phone;

    @Column(name="fax")
    private java.lang.String fax;

    @Column(name="tin", nullable=false)
    private java.lang.String tin;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getEmail() {
        
        return email;
    }

    public void setEmail(java.lang.String email) {

        this.email = email;
    }

    public java.lang.String getPhone() {
        
        return phone;
    }

    public void setPhone(java.lang.String phone) {

        this.phone = phone;
    }

    public java.lang.String getFax() {
        
        return fax;
    }

    public void setFax(java.lang.String fax) {

        this.fax = fax;
    }

    public java.lang.String getTin() {
        
        return tin;
    }

    public void setTin(java.lang.String tin) {

        this.tin = tin;
    }

    public String toString() {
        return name;
    }
}

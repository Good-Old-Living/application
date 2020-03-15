package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="bp_business_partner")
public class BusinessPartner extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="contact_person", nullable=false)
    private java.lang.String contactPerson;

    @Column(name="mobile", nullable=false)
    private Integer mobile;

    @Column(name="landline")
    private Integer landline;

    @Column(name="email")
    private java.lang.String email;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="address_id", nullable=false)
    private app.domain.location.Address address;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getContactPerson() {
        
        return contactPerson;
    }

    public void setContactPerson(java.lang.String contactPerson) {

        this.contactPerson = contactPerson;
    }

    public Integer getMobile() {
        
        return mobile;
    }

    public void setMobile(Integer mobile) {

        this.mobile = mobile;
    }

    public Integer getLandline() {
        
        return landline;
    }

    public void setLandline(Integer landline) {

        this.landline = landline;
    }

    public java.lang.String getEmail() {
        
        return email;
    }

    public void setEmail(java.lang.String email) {

        this.email = email;
    }

    public app.domain.location.Address getAddress() {

        if (address == null) {
        }
        
        return address;
    }

    public void setAddress(app.domain.location.Address address) {

        this.address = address;
    }

    public String toString() {
        return name;
    }
}

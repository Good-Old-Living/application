package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="bp_customer_address")
public class CustomerAddress extends AppEntity {


    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="mobile", nullable=false)
    private java.lang.String mobile;

    @Column(name="alt_phone")
    private java.lang.String altPhone;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="housing_complex_address_id")
    private app.domain.location.HousingComplexAddress housingComplexAddress;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="address_id")
    private app.domain.location.Address address;

    @Column(name="is_primary")
    private String isPrimary = "N";
    private transient boolean isPrimaryBoolean;

    public Long getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;
    }

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getMobile() {
        
        return mobile;
    }

    public void setMobile(java.lang.String mobile) {

        this.mobile = mobile;
    }

    public java.lang.String getAltPhone() {
        
        return altPhone;
    }

    public void setAltPhone(java.lang.String altPhone) {

        this.altPhone = altPhone;
    }

    public app.domain.location.HousingComplexAddress getHousingComplexAddress() {

        if (housingComplexAddress == null) {
        }
        
        return housingComplexAddress;
    }

    public void setHousingComplexAddress(app.domain.location.HousingComplexAddress housingComplexAddress) {

        this.housingComplexAddress = housingComplexAddress;
    }

    public app.domain.location.Address getAddress() {

        if (address == null) {
        }
        
        return address;
    }

    public void setAddress(app.domain.location.Address address) {

        this.address = address;
    }

    public java.lang.String getIsPrimary() {
        
        return isPrimary;
    }

    public void setIsPrimary(java.lang.String isPrimary) {

        this.isPrimary = isPrimary;
    }

    public boolean isPrimary() {

        return "Y".equals(isPrimary);
    }

    public Boolean getIsPrimaryBoolean() {
        
        return isPrimary != null && isPrimary.equals("Y");
    }

    public void setIsPrimaryBoolean(Boolean isPrimaryBoolean) {

        isPrimary = isPrimaryBoolean ? "Y" : "N";
    }

    public boolean isPrimaryBoolean() {

        return "Y".equals(isPrimaryBoolean);
    }

    public String toString() {
        return name;
    }
}

package app.erp;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="bus_enterprise_address")
public class EnterpriseAddress extends AppEntity {


    @Column(name="enterprise_id", nullable=false)
    private java.lang.String enterpriseId;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="address_id", nullable=false)
    private app.domain.location.Address address;

    @Column(name="is_primary")
    private String isPrimary = "N";
    private transient boolean isPrimaryBoolean;

    public java.lang.String getEnterpriseId() {
        
        return enterpriseId;
    }

    public void setEnterpriseId(java.lang.String enterpriseId) {

        this.enterpriseId = enterpriseId;
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
}

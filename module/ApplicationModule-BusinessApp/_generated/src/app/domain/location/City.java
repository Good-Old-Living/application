package app.domain.location;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_city")
public class City extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="country_id", nullable=false)
    private Long countryId;

    @Column(name="state_id", nullable=false)
    private Long stateId;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public Long getCountryId() {
        
        return countryId;
    }

    public void setCountryId(Long countryId) {

        this.countryId = countryId;
    }

    public Long getStateId() {
        
        return stateId;
    }

    public void setStateId(Long stateId) {

        this.stateId = stateId;
    }

    public String toString() {
        return name;
    }
}

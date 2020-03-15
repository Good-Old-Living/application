package app.domain.location;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_city_area")
public class CityArea extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="city_id", nullable=false)
    private Long cityId;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public Long getCityId() {
        
        return cityId;
    }

    public void setCityId(Long cityId) {

        this.cityId = cityId;
    }

    public String toString() {
        return name;
    }
}

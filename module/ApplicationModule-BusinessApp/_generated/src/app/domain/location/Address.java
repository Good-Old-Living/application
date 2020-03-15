package app.domain.location;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="core_address")
public class Address extends AppEntity {


    @Column(name="address", nullable=false)
    private java.lang.String address;

    @Column(name="landmark")
    private java.lang.String landmark;

    @OneToOne
    @JoinColumn(name="area_id")
    private app.domain.location.CityArea area;

    @OneToOne
    @JoinColumn(name="city_id", nullable=false)
    private app.domain.location.City city;

    @OneToOne
    @JoinColumn(name="state_id", nullable=false)
    private app.domain.location.State state;

    @OneToOne
    @JoinColumn(name="country_id", nullable=false)
    private app.domain.location.Country country;

    @Column(name="pin_code", nullable=false)
    private Integer pinCode;

    @Column(name="latlng")
    private java.lang.String latlng;

    public java.lang.String getAddress() {
        
        return address;
    }

    public void setAddress(java.lang.String address) {

        this.address = address;
    }

    public java.lang.String getLandmark() {
        
        return landmark;
    }

    public void setLandmark(java.lang.String landmark) {

        this.landmark = landmark;
    }

    public app.domain.location.CityArea getArea() {
        
        return area;
    }

    public void setArea(app.domain.location.CityArea area) {

        this.area = area;
    }

    public app.domain.location.City getCity() {
        
        return city;
    }

    public void setCity(app.domain.location.City city) {

        this.city = city;
    }

    public app.domain.location.State getState() {
        
        return state;
    }

    public void setState(app.domain.location.State state) {

        this.state = state;
    }

    public app.domain.location.Country getCountry() {
        
        return country;
    }

    public void setCountry(app.domain.location.Country country) {

        this.country = country;
    }

    public Integer getPinCode() {
        
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {

        this.pinCode = pinCode;
    }

    public java.lang.String getLatlng() {
        
        return latlng;
    }

    public void setLatlng(java.lang.String latlng) {

        this.latlng = latlng;
    }
}

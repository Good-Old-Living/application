package app.domain.location;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="core_housing_complex_address")
public class HousingComplexAddress extends AppEntity {


    @Column(name="block", nullable=false)
    private java.lang.String block;

    @Column(name="number", nullable=false)
    private java.lang.String number;

    @OneToOne
    @JoinColumn(name="housing_complex_id", nullable=false)
    private app.domain.location.HousingComplex housingComplex;

    public java.lang.String getBlock() {
        
        return block;
    }

    public void setBlock(java.lang.String block) {

        this.block = block;
    }

    public java.lang.String getNumber() {
        
        return number;
    }

    public void setNumber(java.lang.String number) {

        this.number = number;
    }

    public app.domain.location.HousingComplex getHousingComplex() {
        
        return housingComplex;
    }

    public void setHousingComplex(app.domain.location.HousingComplex housingComplex) {

        this.housingComplex = housingComplex;
    }
}

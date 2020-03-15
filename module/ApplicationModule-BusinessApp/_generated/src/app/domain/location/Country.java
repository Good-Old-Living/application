package app.domain.location;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_country")
public class Country extends AppEntity implements app.entity.Nameable {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="code", nullable=false)
    private java.lang.String code;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getCode() {
        
        return code;
    }

    public void setCode(java.lang.String code) {

        this.code = code;
    }

    public String toString() {
        return name;
    }
}

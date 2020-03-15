package app.domain;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_client")
public class Client implements app.entity.Nameable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="app_id", nullable=false)
    private Long appId;

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

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getAppId() {
        
        return appId;
    }

    public void setAppId(Long appId) {

        this.appId = appId;
    }

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

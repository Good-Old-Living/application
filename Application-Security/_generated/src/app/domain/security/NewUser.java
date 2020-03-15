package app.domain.security;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="sec_new_user")
public class NewUser extends AppEntity {


    @Column(name="name")
    private java.lang.String name;

    @Column(name="email")
    private java.lang.String email;

    @Column(name="mobile")
    private java.lang.String mobile;

    @Column(name="password", nullable=false)
    private java.lang.String password;

    @javax.persistence.Transient
    private transient java.lang.String cpassword;

    @Column(name="email_access_token")
    private java.lang.String emailAccessToken;

    @Column(name="mobile_access_token")
    private java.lang.String mobileAccessToken;

    @OneToOne
    @JoinColumn(name="primary_role_id", nullable=false)
    private app.domain.security.Role primaryRole;

    @Column(name="registered_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar registeredOn;

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

    public java.lang.String getMobile() {
        
        return mobile;
    }

    public void setMobile(java.lang.String mobile) {

        this.mobile = mobile;
    }

    public java.lang.String getPassword() {
        
        return password;
    }

    public void setPassword(java.lang.String password) {

        this.password = password;
    }

    public java.lang.String getCpassword() {
        
        return cpassword;
    }

    public void setCpassword(java.lang.String cpassword) {

        this.cpassword = cpassword;
    }

    public java.lang.String getEmailAccessToken() {
        
        return emailAccessToken;
    }

    public void setEmailAccessToken(java.lang.String emailAccessToken) {

        this.emailAccessToken = emailAccessToken;
    }

    public java.lang.String getMobileAccessToken() {
        
        return mobileAccessToken;
    }

    public void setMobileAccessToken(java.lang.String mobileAccessToken) {

        this.mobileAccessToken = mobileAccessToken;
    }

    public app.domain.security.Role getPrimaryRole() {
        
        return primaryRole;
    }

    public void setPrimaryRole(app.domain.security.Role primaryRole) {

        this.primaryRole = primaryRole;
    }

    public java.util.Calendar getRegisteredOn() {
        
        return registeredOn;
    }

    public void setRegisteredOn(java.util.Calendar registeredOn) {

        this.registeredOn = registeredOn;
    }

    public String toString() {
        return name;
    }
}

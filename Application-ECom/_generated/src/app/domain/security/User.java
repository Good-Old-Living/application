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
@Table(name="sec_user")
public class User extends AppEntity {


    @Column(name="mobile", nullable=false)
    private java.lang.String mobile;

    @Column(name="password", nullable=false)
    private java.lang.String password;

    @OneToOne
    @JoinColumn(name="primary_role_id", nullable=false)
    private app.domain.security.Role primaryRole;

    @Column(name="state", nullable=false)
    private java.lang.String state;

    @Column(name="info")
    private java.lang.String info;

    @Column(name="created_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdOn;

    @Column(name="mobile_access_token")
    private java.lang.String mobileAccessToken;

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

    public app.domain.security.Role getPrimaryRole() {
        
        return primaryRole;
    }

    public void setPrimaryRole(app.domain.security.Role primaryRole) {

        this.primaryRole = primaryRole;
    }

    public java.lang.String getState() {
        
        return state;
    }

    public void setState(java.lang.String state) {

        this.state = state;
    }

    public java.lang.String getInfo() {
        
        return info;
    }

    public void setInfo(java.lang.String info) {

        this.info = info;
    }

    public java.util.Date getCreatedOn() {
        
        return createdOn;
    }

    public void setCreatedOn(java.util.Date createdOn) {

        this.createdOn = createdOn;
    }

    public java.lang.String getMobileAccessToken() {
        
        return mobileAccessToken;
    }

    public void setMobileAccessToken(java.lang.String mobileAccessToken) {

        this.mobileAccessToken = mobileAccessToken;
    }
}

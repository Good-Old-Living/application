package app.domain.security;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="sec_user")
public class User extends AppEntity {


    @Column(name="name")
    private java.lang.String name;

    @Column(name="email")
    private java.lang.String email;

    @Column(name="mobile")
    private java.lang.String mobile;

    @Column(name="password")
    private java.lang.String password;

    @Column(name="access_token")
    private java.lang.String accessToken;

    @OneToOne
    @JoinColumn(name="primary_role_id", nullable=false)
    private app.domain.security.Role primaryRole;

    @Column(name="state", nullable=false)
    private java.lang.String state;

    @Column(name="info")
    private java.lang.String info;

    @Column(name="provider")
    private java.lang.String provider;

    @Column(name="provider_id")
    private java.lang.String providerId;

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

    public java.lang.String getAccessToken() {
        
        return accessToken;
    }

    public void setAccessToken(java.lang.String accessToken) {

        this.accessToken = accessToken;
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

    public java.lang.String getProvider() {
        
        return provider;
    }

    public void setProvider(java.lang.String provider) {

        this.provider = provider;
    }

    public java.lang.String getProviderId() {
        
        return providerId;
    }

    public void setProviderId(java.lang.String providerId) {

        this.providerId = providerId;
    }

    public String toString() {
        return name;
    }
}

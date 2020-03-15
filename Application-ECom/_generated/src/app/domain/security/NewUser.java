package app.domain.security;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sec_new_user")
public class NewUser extends AppEntity {


    @Column(name="mobile", nullable=false)
    private java.lang.String mobile;

    @javax.persistence.Transient
    private transient java.lang.String otp;

    @Column(name="mobile_access_token", nullable=false)
    private java.lang.String mobileAccessToken;

    public java.lang.String getMobile() {
        
        return mobile;
    }

    public void setMobile(java.lang.String mobile) {

        this.mobile = mobile;
    }

    public java.lang.String getOtp() {
        
        return otp;
    }

    public void setOtp(java.lang.String otp) {

        this.otp = otp;
    }

    public java.lang.String getMobileAccessToken() {
        
        return mobileAccessToken;
    }

    public void setMobileAccessToken(java.lang.String mobileAccessToken) {

        this.mobileAccessToken = mobileAccessToken;
    }
}

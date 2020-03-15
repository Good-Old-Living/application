package app.erp.mdm.bp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="bp_customer")
public class Customer extends AppEntity {


    @Column(name="name")
    private java.lang.String name;

    @Column(name="email")
    private java.lang.String email;

    @Column(name="mobile", nullable=false)
    private java.lang.String mobile;

    @OneToOne
    @JoinColumn(name="group_id")
    private app.erp.mdm.bp.CustomerGroup group;

    @Column(name="user_id", nullable=false)
    private Long userId;

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

    public app.erp.mdm.bp.CustomerGroup getGroup() {
        
        return group;
    }

    public void setGroup(app.erp.mdm.bp.CustomerGroup group) {

        this.group = group;
    }

    public Long getUserId() {
        
        return userId;
    }

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    public String toString() {
        return name;
    }
}

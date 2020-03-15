package app.domain.security;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sec_user_role")
public class UserRole extends AppEntity {


    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="role_id", nullable=false)
    private Long roleId;

    public Long getUserId() {
        
        return userId;
    }

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    public Long getRoleId() {
        
        return roleId;
    }

    public void setRoleId(Long roleId) {

        this.roleId = roleId;
    }
}

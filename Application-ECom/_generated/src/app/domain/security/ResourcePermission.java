package app.domain.security;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="sec_resource_permission")
public class ResourcePermission extends AppEntity {


    @Column(name="resource_security_id", nullable=false)
    private Long resourceSecurityId;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="role_id", nullable=false)
    private app.domain.security.Role role;

    public Long getResourceSecurityId() {
        
        return resourceSecurityId;
    }

    public void setResourceSecurityId(Long resourceSecurityId) {

        this.resourceSecurityId = resourceSecurityId;
    }

    public app.domain.security.Role getRole() {

        if (role == null) {
        }
        
        return role;
    }

    public void setRole(app.domain.security.Role role) {

        this.role = role;
    }
}

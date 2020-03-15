package meru.application.security.guard;

import meru.app.AppRequest;
import meru.application.security.BaseSecurityGuard;
import meru.exception.LoginRequiredException;
import app.domain.security.ResourceSecurity;

public class ProtectedResourceGuard extends BaseSecurityGuard {

    @Override
    protected void enforce(AppRequest appRequest) {
        if (!appRequest.isAuthenticated()) {
            throw new LoginRequiredException();
        }
    }

    @Override
    protected boolean handles(ResourceSecurity resourceSecurity) {
        return resourceSecurity.getIsProtectedBoolean();
    }
}

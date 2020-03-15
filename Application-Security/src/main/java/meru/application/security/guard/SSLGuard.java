package meru.application.security.guard;

import meru.app.AppRequest;
import meru.application.security.BaseSecurityGuard;
import meru.exception.AppException;
import app.domain.security.ResourceSecurity;

public class SSLGuard extends BaseSecurityGuard {

    private static final String SSL = "ssl";

    @Override
    protected void enforce(AppRequest appRequest) {
        if (!appRequest.isSSLEnabled()) {
            throw new AppException("The request must be sent via SSL");
        }
    }

    @Override
    protected boolean handles(ResourceSecurity resourceSecurity) {
        return SSL.equalsIgnoreCase(resourceSecurity.getAccessType());
    }

}

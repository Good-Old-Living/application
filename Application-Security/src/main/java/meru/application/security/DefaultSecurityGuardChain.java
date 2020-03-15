package meru.application.security;

import java.util.ArrayList;
import java.util.List;

import meru.app.AppRequest;
import meru.app.engine.AppEngine;
import meru.app.security.SecurityGuard;
import meru.app.security.SecurityGuardChain;
import meru.application.security.guard.ProtectedResourceGuard;
import meru.application.security.guard.SSLGuard;
import meru.persistence.EntityQuery;
import app.domain.security.ResourceSecurity;

public class DefaultSecurityGuardChain extends SecurityGuardChain {

    private List<SecurityGuard> mSecurityGuards = new ArrayList<SecurityGuard>(2);

    private DefaultSecurityGuardChain() {

    }

    public static final SecurityGuardChain createSecurityGuardChain(AppEngine appEngine) {

        SecurityGuardChain securityGuardChain = new DefaultSecurityGuardChain();
        securityGuardChain.chainSecurityGuard(new SSLGuard());
        securityGuardChain.chainSecurityGuard(new ProtectedResourceGuard());

//        EntityQuery<ResourceSecurity> entityQuery = appEngine.createQuery(ResourceSecurity.class);
//        List<ResourceSecurity> resourceSecurityList = appEngine.get(entityQuery);
//        for (ResourceSecurity resourceSecurity : resourceSecurityList) {
//            for (SecurityGuard securityGaurd : securityGuardChain.getSecurityGuards()) {
//                ((BaseSecurityGuard) securityGaurd).addResourceSecurity(resourceSecurity);
//            }
//        }

        return securityGuardChain;

    }

    public SecurityGuardChain chainSecurityGuard(SecurityGuard securityGuard) {
        mSecurityGuards.add(securityGuard);
        return this;
    }

    public void guard(AppRequest appRequest) {
        for (SecurityGuard securityGuard : mSecurityGuards) {
            securityGuard.guard(appRequest);
        }
    }
}

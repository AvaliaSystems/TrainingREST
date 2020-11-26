package ch.heigvd.gamification.api.util;


import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String principalHeaderRequest;

    public APIKeyAuthFilter(String principalHeaderRequest) {
        this.principalHeaderRequest = principalHeaderRequest;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(principalHeaderRequest);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return "not implemented";
    }
}

package com.jlife.abon.security;

import com.jlife.abon.controller.application.Application;
import com.jlife.abon.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Khralovich Dzmitry
 */
public class FormSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private Application application;

    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        application.updateCurrentUserInSession();
        if (roles.contains(Role.ROLE_COMPANY.name())) {
            application.updateCompanyInSession();
        } else if (roles.contains(Role.ROLE_USER.name())) {
            application.setCurrentCardToSession();
        }
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        clearAuthenticationAttributes(request);
        if (savedRequest == null) {
            response.sendRedirect("/");
            return;
        }
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

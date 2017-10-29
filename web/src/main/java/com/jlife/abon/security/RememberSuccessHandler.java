package com.jlife.abon.security;

import com.jlife.abon.controller.application.Application;
import com.jlife.abon.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Khralovich Dzmitry
 */
public class RememberSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private Application application;

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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getRequestURI());
        requestDispatcher.forward(request, response);
    }
}

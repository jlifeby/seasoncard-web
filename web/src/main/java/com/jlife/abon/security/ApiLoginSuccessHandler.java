package com.jlife.abon.security;

import com.google.gson.Gson;
import com.jlife.abon.controller.application.Application;
import com.jlife.abon.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Khralovich Dzmitry
 */
public class ApiLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private Application application;

    @Autowired
    private Gson gson;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setSuccess(true);
        if (roles.contains("ROLE_COMPANY")) {
            authResponse.setRole(Role.ROLE_COMPANY);
            application.updateCompanyInSession();
        } else if (roles.contains(Role.ROLE_USER.name())) {
            application.setCurrentCardToSession();
            application.updateCurrentUserInSession();
            authResponse.setRole(Role.ROLE_USER);
        } else if (roles.contains(Role.ROLE_ADMIN.name())) {
            authResponse.setRole(Role.ROLE_ADMIN);
        }
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        String json = gson.toJson(authResponse);
        response.getWriter().write(json);
        response.getWriter().close();
    }
}

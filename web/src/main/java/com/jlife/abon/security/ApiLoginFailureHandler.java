package com.jlife.abon.security;

import com.google.gson.Gson;
import com.jlife.abon.controller.application.Application;
import com.jlife.abon.error.ApiErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Khralovich Dzmitry
 */
public class ApiLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private Application application;

    @Autowired
    private Gson gson;

    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setSuccess(false);
        authResponse.setApiErrorCode(ApiErrorCode.BAD_AUTHENTICATION_DATA);
        authResponse.setMsg("Bad authentication data");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(gson.toJson(authResponse));
        response.getWriter().close();
    }
}

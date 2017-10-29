package com.jlife.abon.security;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzmitry Misiuk
 */
public class ApiLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private Gson gson;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //super.onLogoutSuccess(request, response, authentication);
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setSuccess(true);
        logoutResponse.setMsg("You log out successfully");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        String json = gson.toJson(logoutResponse);
        response.getWriter().write(json);
        response.getWriter().close();
    }
}

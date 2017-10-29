package com.jlife.abon.security;

import com.google.gson.Gson;
import com.jlife.abon.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzmitry Misiuk
 */
public class ApiForbiddedEntryPoint extends Http403ForbiddenEntryPoint {

    @Autowired
    private Gson gson;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiResponse errorResult = new ApiResponse();
        errorResult.setMsg("you should be authenticated");
        response.getWriter().write(gson.toJson(errorResult));
        response.getWriter().close();
    }
}

package com.jlife.abon.security;

import com.google.gson.Gson;
import com.jlife.abon.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzmitry Misiuk
 */
public class ApiAccessDeniedHandler implements AccessDeniedHandler{

    @Autowired
    private Gson gson;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiResponse errorResult = new ApiResponse();
        errorResult.setMsg("You don't have access");
        response.getWriter().write(gson.toJson(errorResult));
        response.getWriter().close();
    }
}

package com.jlife.abon.controller;

import com.jlife.abon.error.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@Controller
public class ErrorController {

    private final static Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/common-error")
    public String redirectError(HttpServletRequest request,
                                HttpServletResponse response) {
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (throwable != null && throwable.getCause() instanceof ResourceNotFoundException) {
            statusCode = 404;
            response.setStatus(statusCode);
            request.setAttribute("javax.servlet.error.status_code", statusCode);
        }
        String msgTemplate = "SERVER ERROR: HTTP Code: %s";

        LOG.error(String.format(msgTemplate, statusCode), throwable);
        return "common-error";
    }

}

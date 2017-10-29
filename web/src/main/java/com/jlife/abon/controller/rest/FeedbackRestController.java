package com.jlife.abon.controller.rest;

import com.jlife.abon.api.ApiResponse;
import com.jlife.abon.facade.FeedbackFacade;
import com.jlife.abon.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api")
public class FeedbackRestController {

    @Autowired
    private FeedbackFacade feedbackFacade;

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ApiResponse feedbackSend(@ModelAttribute("email") String email,
                                    @ModelAttribute("subject") String subject,
                                    @ModelAttribute("message") String message) {
        if (email != null && SecurityService.isValidEmail(email)) {
            feedbackFacade.sendFeedback(email, email, subject, message);
        } else {
            feedbackFacade.sendFeedback(subject, message);
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        return apiResponse;
    }
}

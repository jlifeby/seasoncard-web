package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.facade.FeedbackFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dzmitry Misiuk
 */
@Controller
public class FeedbackController extends BaseController {

    @Autowired
    private FeedbackFacade feedbackFacade;

    @RequestMapping(value = "/feedback/send", method = RequestMethod.POST)
    public String feedbackSend(@ModelAttribute("name") String name,
                               @ModelAttribute("email") String email,
                               @ModelAttribute("subject") String subject,
                               @ModelAttribute("message") String message,
                               final HttpServletRequest request,
                               RedirectAttributes redirectAttrs) {
        feedbackFacade.sendFeedback(name, email, subject, message);
        String flashMessage = "Ваше письмо отправлено! Мы свяжемся с Вами в ближайшее время.";
        redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
        String referrer = request.getHeader("referer");
        return String.format("redirect:%s", referrer);
    }
}

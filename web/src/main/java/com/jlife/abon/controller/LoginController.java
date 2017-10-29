package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.RecoveringPasswordFacade;
import com.jlife.abon.form.RecoveringPasswordForm;
import com.jlife.abon.form.RecoveringRequestForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Kiryl Bokiy
 * @author Dzmitry Misiuk
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private RecoveringPasswordFacade recoveringPasswordFacade;

    @InitBinder /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model, HttpServletRequest request) throws ServletException {
        if (!isAnonymous()) {
            request.logout();
            return "redirect:/login";
        }
        model.addAttribute("login", true);
        return "login";
    }

    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String loginError(ModelMap model, HttpServletRequest request) throws ServletException {
        if (!isAnonymous()) {
            request.logout();
            return "redirect:/login";
        }
        model.addAttribute("error", true);
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "main";
    }

    @RequestMapping(value = "/recover-password-request", method = RequestMethod.GET)
    public String getRecoverReqeustPage(ModelMap model) {
        model.addAttribute("recoveringRequestForm", new RecoveringRequestForm());
        return "recover-password-request";
    }

    @RequestMapping(value = "/recover-password-request", method = RequestMethod.POST)
    public String recoverPassword(@Valid @ModelAttribute("recoveringRequestForm") RecoveringRequestForm recoveringRequestForm,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("recoveringRequestForm", recoveringRequestForm);
            return "recover-password-request";
        }
        try {
            if (StringUtils.isNotBlank(recoveringRequestForm.getPhone()) || recoveringRequestForm.getCardUUID() != null) {
                String phone = recoveringRequestForm.getPhone();
                Long cardUUID = recoveringRequestForm.getCardUUID();

                recoveringPasswordFacade.recoverPasswordWithPhone(phone, cardUUID);

                String flashMsg = String.format("На ваш телефон %s была выслана SMS с новым паролем", phone);
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, flashMsg);
                return "redirect:/login";
            } else if (StringUtils.isNotBlank(recoveringRequestForm.getEmail())) {
                String email = recoveringRequestForm.getEmail();
                recoveringPasswordFacade.recoverPassword(email);
                String flashMsg = String.format("На ваш email %s была выслана ссылка для восстановления пароля", email);
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, flashMsg);
                return "redirect:/login";
            }

            throw new AbonRuntimeException();

        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("recoveringRequestForm", recoveringRequestForm);
            return "recover-password-request";
        }
    }

    @RequestMapping(value = "/recover-password", method = RequestMethod.GET)
    public String getChangingPasswordByRecoveringPage(@RequestParam(name = "recoveringId", required = true) String recoveringId,
                                                      Model model,
                                                      RedirectAttributes redirectAttributes) {
        boolean isExist = recoveringPasswordFacade.isExistRecoveringId(recoveringId);
        if (!isExist) {
            String flashMsg = "Ссылка на восстановление доступа не действительна";
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, flashMsg);
            return "redirect:/";
        }
        RecoveringPasswordForm recoveringPasswordForm = new RecoveringPasswordForm();
        recoveringPasswordForm.setRecoveringId(recoveringId);

        model.addAttribute("recoveringPasswordForm", recoveringPasswordForm);
        return "recover-password";
    }

    @RequestMapping(value = "/recover-password", method = RequestMethod.POST)
    public String changePasswordByRecovering(@Valid @ModelAttribute("recoveringPasswordForm") RecoveringPasswordForm recoveringPasswordForm,
                                             BindingResult result,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {
        String newPassword = recoveringPasswordForm.getNewPassword();
        String recoveringId = recoveringPasswordForm.getRecoveringId();
        if (result.hasErrors()) {
            model.addAttribute("recoveringPasswordForm", recoveringPasswordForm);
            return "recover-password";
        }
        try {
            recoveringPasswordFacade.changePassword(recoveringId, newPassword);
            String flashMsg = "Ваш пароль успешно изменен";
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, flashMsg);
            return "redirect:/login";
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("recoveringPasswordForm", recoveringPasswordForm);
            return "recover-password";
        }
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(ModelMap model) {
//        return "registration";
//    }

    private boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ||
                (authentication instanceof AnonymousAuthenticationToken);
    }
}


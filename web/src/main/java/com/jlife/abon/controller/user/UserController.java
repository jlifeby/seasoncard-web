package com.jlife.abon.controller.user;

import com.jlife.abon.controller.application.Application;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.dto.UserSettingsData;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.ChangingEmailFacade;
import com.jlife.abon.facade.ChangingPasswordFacade;
import com.jlife.abon.facade.UserFacade;
import com.jlife.abon.form.AccountForm;
import com.jlife.abon.form.ChangingEmailForm;
import com.jlife.abon.form.ChangingPasswordForm;
import com.jlife.abon.form.UserSettingsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private Application application;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ChangingEmailFacade changingEmailFacade;

    @Autowired
    private ChangingPasswordFacade changingPasswordFacade;

    @RequestMapping(value = "/account")
    public String getMyAccount(ModelMap model) {
        model.put("accountForm", AccountForm.fromUser(getCurrentUser()));
        model.put("card", getUserCard());
        return "user-account";
    }

    @RequestMapping(value = "/account/save", method = RequestMethod.POST)
    public String saveUser(Model model,
                           @Valid @ModelAttribute("accountForm") AccountForm accountForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "user-account";
        }
        try {
            UserData user = accountForm.toUser();
            userFacade.updateOwnProfileAsUser(user, getCurrentUser().getId());
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Ваш аккаунт успешно сохранен");
            getApplication().updateCurrentUserInSession();
            return "redirect:/account";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving account", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "user-account";
        }
    }

    @RequestMapping(value = "/account/change-email", method = RequestMethod.GET)
    public String getChangingEmailPage(Model model) {
        ChangingEmailForm changingEmailForm = new ChangingEmailForm();
        model.addAttribute("changingEmailForm", changingEmailForm);
        UserData currentUser = getCurrentUser();
        boolean isRequiredAgreement = !currentUser.isAcceptedAgreement() & currentUser.hasUserRole();
        if (isRequiredAgreement) {
            changingEmailForm.setNewEmail(currentUser.getNewEmail());
        }
        return "user-account-change-email";
    }

    @RequestMapping(value = "/account/change-email", method = RequestMethod.POST)
    public String changeEmail(Model model,
                              @Valid @ModelAttribute("changingEmailForm") ChangingEmailForm changingEmailForm,
                              BindingResult result,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "user-account-change-email";
        }
        String newEmail = changingEmailForm.getNewEmail();
        try {
            changingEmailFacade.changeEmail(getCurrentUser().getId(), newEmail);
            getApplication().updateCurrentUserInSession();
            String flashMessage = String.format(
                    "На Ваш email %s отправлено письмо с подтверждением изменения.", newEmail);
            redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
            return "redirect:/account";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on changing email", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "user-account-change-email";
        }
    }

    @RequestMapping(value = "/account/change-email-with-agreement", method = RequestMethod.POST)
    public String changeEmailWithAgreement(Model model,
                                           @Valid @ModelAttribute("changingEmailForm") ChangingEmailForm changingEmailForm,
                                           @ModelAttribute("acceptedAgreement") boolean acceptedAgreement,
                                           BindingResult result,
                                           RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "user-account-change-email";
        }
        if (!acceptedAgreement) {
            model.addAttribute(ERROR_MESSAGE, "Вы должны согласиться с Пользовательским Соглашением");
            return "user-account-change-email";
        }
        String newEmail = changingEmailForm.getNewEmail();
        try {
            changingEmailFacade.changeEmailWithAcceptingAgreement(getCurrentUser().getId(), newEmail);
            getApplication().updateCurrentUserInSession();
            String flashMessage = String.format(
                    "На Ваш email %s отправлено письмо с подтверждением изменения.", newEmail);
            redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
            return "redirect:/user/abonnements";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on changing email", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "user-account-change-email";
        }
    }

    @RequestMapping(value = "/confirm-email-changing/{confirmationId}")
    public String confirmChangingEmail(@PathVariable("confirmationId") String confirmationId,
                                       HttpServletRequest httpServletRequest,
                                       RedirectAttributes redirectAttrs) {
        try {
            httpServletRequest.logout();
            changingEmailFacade.confirmChangingEmail(confirmationId);
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE,
                    "Ваш email успешно изменен");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on confirmation message", e);
            redirectAttrs.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        } catch (ServletException e) {
            LOG.error("Error on logout user", e);
            redirectAttrs.addFlashAttribute(ERROR_MESSAGE, "Внутренняя ошибка сервера");
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/account/change-password", method = RequestMethod.GET)
    public String getChangingPasswordPage(Model model) {
        model.addAttribute("changingPasswordForm", new ChangingPasswordForm());
        return "user-account-change-password";
    }

    @RequestMapping(value = "/account/change-password", method = RequestMethod.POST)
    public String changePassword(Model model,
                                 @Valid @ModelAttribute("changingPasswordForm") ChangingPasswordForm changingPasswordForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttrs) {
        String currentPassword = changingPasswordForm.getCurrentPassword();
        String newPassword = changingPasswordForm.getNewPassword();
        if (result.hasErrors()) {
            return "user-account-change-password";
        }
        try {
            changingPasswordFacade.changePassword(getCurrentUser().getId(), currentPassword, newPassword);
            application.updateCurrentUserInSession();
            String flashMessage = "Ваш пароль изменен";
            redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
            return "redirect:/account";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on changin email", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "user-account-change-password";
        }
    }

    @RequestMapping(value = "/account/settings", method = RequestMethod.GET)
    public String getUserAccountSettings(ModelMap model) {
        model.put("userSettingsForm", new UserSettingsForm(getCurrentUser()));
        return "user-account-settings";
    }

    @RequestMapping(value = "/account/settings", method = RequestMethod.POST)
    public String changeUserAccountSettings(Model model,
                                            @Valid @ModelAttribute("userSettingsForm") UserSettingsForm userSettingsForm,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "user-account-settings";
        }
        try {
            UserSettingsData userSettings = userSettingsForm.toUserSettings();
            userFacade.updateOwnSettingsAsUser(userSettings, getCurrentUser().getId());
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Ваши настройки успешно обновлены");
            getApplication().updateCurrentUserInSession();
            return "redirect:/account";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving user settings", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "user-account-settings";
        }
    }
}

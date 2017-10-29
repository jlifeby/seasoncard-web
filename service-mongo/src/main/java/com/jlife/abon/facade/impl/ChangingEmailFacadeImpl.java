package com.jlife.abon.facade.impl;

import com.jlife.abon.entity.User;
import com.jlife.abon.facade.ChangingEmailFacade;
import com.jlife.abon.service.EmailService;
import com.jlife.abon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade to work with changing email for user.
 *
 * @author Dzmitry Misiuk
 */
@Component
public class ChangingEmailFacadeImpl implements ChangingEmailFacade {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Override
    public void changeEmail(String userId, String newEmail) {
        User user = userService.getUser(userId);
        String confirmationId = userService.setNewEmail(userId, newEmail);
        emailService.sendEmailChangingConfirmation(user, confirmationId, newEmail);
    }

    @Override
    public void confirmChangingEmail(String confirmationId) {
        userService.confirmChangingEmail(confirmationId);
    }

    @Override
    public void changeEmailWithAcceptingAgreement(String userId, String newEmail) {
        User user = userService.getUser(userId);
        String confirmationId = userService.setNewEmail(userId, newEmail);
        emailService.sendEmailChangingConfirmation(user, confirmationId, newEmail);
        userService.acceptAgreementByUser(userId);
    }
}

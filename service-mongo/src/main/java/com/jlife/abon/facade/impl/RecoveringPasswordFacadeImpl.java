package com.jlife.abon.facade.impl;

import com.jlife.abon.entity.User;
import com.jlife.abon.facade.RecoveringPasswordFacade;
import com.jlife.abon.service.EmailService;
import com.jlife.abon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade to recover password
 *
 * @author Dzmitry Misiuk
 */
@Component
public class RecoveringPasswordFacadeImpl implements RecoveringPasswordFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override public void recoverPassword(String email) {
        User user = userService.getUserByEmail(email);
        String recoveringId = userService.setRecoveringId(email);
        emailService.sendRecoveringPassword(user, recoveringId);
    }

    @Override public void changePassword(String recoveringId, String newPassword) {
        userService.setNewPasswordByRecovering(recoveringId, newPassword);
    }

    @Override public boolean isExistRecoveringId(String recoveringId) {
        return userService.isExistRecoveringId(recoveringId);
    }

    @Override public void recoverPasswordWithPhone(String phone, Long cardUUID) {
        userService.recoverPasswordWithPhone(phone, cardUUID);
    }
}

package com.jlife.abon.facade.impl;

import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.facade.ChangingPasswordFacade;
import com.jlife.abon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade to change user password
 *
 * @author Dzmitry Misiuk
 */
@Component
public class ChangingPasswordFacadeImpl implements ChangingPasswordFacade {

    @Autowired
    private UserService userService;

    @Override public void changePassword(String userId, String currentPassword, String newPassword) {
        boolean isAuthenticated = userService.authenticate(userId, currentPassword);
        if (isAuthenticated) {
            userService.setNewPasswordByUser(userId, newPassword);
        } else {
            throw new NotAllowedException(ApiErrorCode.INCORRECT_CURRENT_PASSWORD);
        }
    }
}

package com.jlife.abon.facade;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ChangingPasswordFacade {
    void changePassword(String userId, String currentPassword, String newPassword);
}

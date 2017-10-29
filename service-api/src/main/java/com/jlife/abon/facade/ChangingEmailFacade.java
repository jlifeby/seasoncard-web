package com.jlife.abon.facade;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ChangingEmailFacade {
    void changeEmail(String userId, String newEmail);

    void confirmChangingEmail(String confirmationId);

    void changeEmailWithAcceptingAgreement(String userId, String newEmail);
}

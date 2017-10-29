package com.jlife.abon.facade;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface RecoveringPasswordFacade {
    void recoverPassword(String email);

    void changePassword(String recoveringId, String newPassword);

    boolean isExistRecoveringId(String recoveringId);

    void recoverPasswordWithPhone(String phone, Long cardUUID);
}

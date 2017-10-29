package com.jlife.abon.facade;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ChangingPhoneFacade {
    void changeClientPhone(Long cardUUID, String newPhone, String companyId);
}

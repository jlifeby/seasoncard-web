package com.jlife.abon.facade;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface FeedbackFacade {
    void sendFeedback(String subject, String message);

    void sendFeedback(String name, String email, String subject, String message);
}

package com.jlife.abon.facade.impl;

import com.jlife.abon.facade.FeedbackFacade;
import org.springframework.stereotype.Service;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class FeedbackFacadeImpl extends AbstractFacade implements FeedbackFacade {


    @Override public void sendFeedback(String subject, String message) {
        emailService.sendAnonymousFeedback(subject, message);
    }

    @Override public void sendFeedback(String name, String email, String subject, String message) {
        emailService.sendFeedback(name, email, subject, message);
    }
}

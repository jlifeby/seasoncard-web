package com.jlife.abon.facade;

import com.jlife.abon.dto.SmsMessageData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface SmsFacade {
    List<SmsMessageData> getAllMessages();

    SmsMessageData getMessage(String id);

    void sendMessage(String phone, String content);

}

package com.jlife.abon.audit;

import com.jlife.abon.controller.application.Application;
import com.jlife.abon.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

public class UserIdBasedAuditor implements AuditorAware<String> {

    @Autowired
    private Application application;

    @Override
    public String getCurrentAuditor() {
        UserData currentUser = application.getCurrentUser();
        return currentUser != null? currentUser.getId(): null;
    }
}
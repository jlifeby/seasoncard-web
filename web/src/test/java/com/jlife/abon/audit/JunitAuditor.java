package com.jlife.abon.audit;

import org.springframework.data.domain.AuditorAware;

public class JunitAuditor implements AuditorAware<String> {

    public static final String JUNIT_AUDITOR = "Junit";

    @Override
    public String getCurrentAuditor() {
        return JUNIT_AUDITOR;
    }
}
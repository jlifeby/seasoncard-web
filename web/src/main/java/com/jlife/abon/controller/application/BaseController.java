package com.jlife.abon.controller.application;

import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.ClientFacade;
import com.jlife.abon.facade.CompanyActivityFacade;
import com.jlife.abon.facade.CompanyFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;

/**
 * @author Khralovich Dzmitry
 */
public abstract class BaseController {

    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String FLASH_MESSAGE = "flashMessage";

    protected final static Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Resource(name = "messageSource")
    protected MessageSource messageSource;
    @Autowired
    protected ClientFacade clientFacade;
    @Autowired
    protected Application application;

    @Autowired
    protected CompanyFacade companyFacade;
    @Autowired
    protected CompanyActivityFacade companyActivityFacade;

    public Application getApplication() {
        return application;
    }

    public UserData getCurrentUser() {
        return application.getCurrentUser();
    }

    public CardData getUserCard() {
        return application.getCurrentCard();
    }

    public String getSessionCompanyId() {
        return application.getCompanyId();
    }

    public CompanyData getCurrentCompany() {
        return this.application.getCurrentCompany();
    }

}

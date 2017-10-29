package com.jlife.abon.controller.application;

import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.UserFacade;
import com.jlife.abon.security.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author Dzmitry Khralovich
 */
@Component()
@Scope("singleton")
public class Application implements java.io.Serializable {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private CardFacade cardFacade;

    @Autowired
    private ApplicationContext applicationContext;

    public Application() {
    }

    public UserData getCurrentUser() {
        String userName = getCurrentUserName();
        if (userName == null) {
            return null;
        }
        if (SecurityService.isValidEmail(userName)) {
            return userFacade.findByEmail(userName);
        }
        if (SecurityService.isValidCardUUID(userName)) {
            return userFacade.findOneByCardUUID(Long.parseLong(userName));
        }
        return null;
    }

    public CardData getCurrentCard() {
        String userId = getCurrentUser().getId();
        return cardFacade.findByUserId(userId);
    }

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            org.springframework.security.core.userdetails.User userdetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String name = userdetails.getUsername();
            return name;
        } else {
            return null;
        }
    }

    public HttpSession getHttpSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public void updateCompanyInSession() {
        HttpSession session = getHttpSession();
        UserData user = getCurrentUser();
        if (StringUtils.isNotBlank(user.getCompanyId())) {
            CompanyData company = companyFacade.getCompany(user.getCompanyId());
            session.setAttribute("company", company);
        }
        updateCompanyId();
    }

    public void updateCurrentUserInSession() {
        HttpSession session = getHttpSession();
        session.setAttribute("currentUser", getCurrentUser());
    }

    public void setCurrentCardToSession() {
        HttpSession session = getHttpSession();
        session.setAttribute("currentCard", getCurrentCard());
    }

    public CompanyData getCurrentCompany() {
        return (CompanyData) getHttpSession().getAttribute("company");
    }

    public String getCompanyId() {
        return getSessionApplication().getCompanyId();
    }

    public void updateCompanyId() {
        this.getSessionApplication().setCompanyId(getCurrentUser().getCompanyId());
    }

    public SessionApplication getSessionApplication() {
        return applicationContext.getBean(SessionApplication.class);
    }

    public Set<String> getCartProducts() {
        return getSessionApplication().getCartProducts();
    }

    public void addProductToCart(String productId) {
        getSessionApplication().getCartProducts().add(productId);
    }

    public void removeProductFromCart(String productId) {
        getSessionApplication().getCartProducts().remove(productId);
    }

    public void clearCart() {
        getSessionApplication().getCartProducts().clear();
    }

}
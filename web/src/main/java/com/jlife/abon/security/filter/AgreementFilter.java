package com.jlife.abon.security.filter;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.enumeration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Filter to redirect to change email if it has not confirmed
 *
 * @author Dzmitry Misiuk
 */
public class AgreementFilter implements Filter {

    public static final String OFFER_VERSION_INIT_PARAM = "publicOfferVersion";
    public static final String AGREEMNET_VERSION_INIT_PARAM = "agreementVersion";


    public static final String ACCOUNT_CHANGE_EMAIL_URL = "/account/change-email";
    public static final String ACCEPT_PAGE_FOR_COMPANY_URL = "/company/accept";
    public static final String CONFIRM_EMAIL_URL = "/confirm-email-changing/";
    public static final String RECOVER_PASSWORD_URL = "/recover-password";
    public static final String COMMON_ERROR_URL = "/common-error";
    public static final String USER_AGREEMENT_URL = "/user-agreement";
    public static final String PUBLIC_OFFER_URL = "/public-offer";
    public static final String JS_URL = "/js/";
    public static final String IMAGES_URL = "/images/";
    public static final String CSS_URL = "/css/";
    public static final String FONTS_URL = "/font/";
    public static final String API_URL = "/api/";
    public static final String FAVICON_URL = "/favicon.ico";


    private String offerVersion;
    private String agreementVersion;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.offerVersion = filterConfig.getInitParameter(OFFER_VERSION_INIT_PARAM);
        this.agreementVersion = filterConfig.getInitParameter(AGREEMNET_VERSION_INIT_PARAM);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String uri = request.getRequestURI();
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (!uri.startsWith(ACCOUNT_CHANGE_EMAIL_URL)
                    && !uri.startsWith(CONFIRM_EMAIL_URL)
                    && !uri.startsWith(RECOVER_PASSWORD_URL)
                    && !uri.startsWith(COMMON_ERROR_URL)
                    && !uri.startsWith(USER_AGREEMENT_URL)
                    && !uri.startsWith(JS_URL)
                    && !uri.startsWith(CSS_URL)
                    && !uri.startsWith(IMAGES_URL)
                    && !uri.startsWith(FONTS_URL)
                    && !uri.startsWith(FAVICON_URL)
                    && !uri.startsWith(API_URL)
                    && !uri.startsWith(ACCEPT_PAGE_FOR_COMPANY_URL)
                    && !uri.startsWith(PUBLIC_OFFER_URL)) {
                if (roles.contains(Role.ROLE_USER.name())) {
                    UserData currentUser = (UserData) request.getSession().getAttribute("currentUser");
                    if ((!currentUser.hasAlreadyConfirmedEmail()
                            && !currentUser.isWaitingToConfirmEmail())
                            || !currentUser.isAcceptedAgreement()) {
                        response.sendRedirect(ACCOUNT_CHANGE_EMAIL_URL);
                    }
                } else if (roles.contains(Role.ROLE_COMPANY.name())) {
                    CompanyData currentCompany = (CompanyData) request.getSession().getAttribute("company");
                    if (!currentCompany.checkIsAcceptedAgreementVersion(agreementVersion)
                            || !currentCompany.checkIsAcceptedPublicOfferVersion(offerVersion)) {
                        response.sendRedirect(ACCEPT_PAGE_FOR_COMPANY_URL);
                    }
                }
            }
        }
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }
}

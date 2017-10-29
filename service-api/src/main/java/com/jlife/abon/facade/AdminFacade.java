package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.CompanyRequisitesData;
import com.jlife.abon.dto.UserData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface AdminFacade {
    CompanyData createCompany(CompanyData company);

    CompanyData updateCompany(String companyId, CompanyData company);

    List<Long> addNfcCardsToCompany(String companyId, long fromCard, long toCard);

    List<Long> addVirtualCardsToCompany(String companyId, int count);

    UserData addCompanyAdmin(String companyId, UserData user);

    UserData updateCompanyAdmin(String companyId, String userId, UserData user);

    UserData getCompanyAdmin(String companyId, String userId);

    List<UserData> getCompanyAdmins(String companyId);

    void sendCompanyAdminCredentials(String companyId, String userId);

    List<Long> removeFreeNfcCardsFromCompany(String companyId, long fromCard, long toCard);

    List<Long> removeFreeVirtualCardsFromCompany(String companyId, long fromCard, long toCard);

    CompanyData updateCompanyRequisites(String companyId, CompanyRequisitesData companyRequisites);
}

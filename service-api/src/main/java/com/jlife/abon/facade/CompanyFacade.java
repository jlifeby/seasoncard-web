package com.jlife.abon.facade;

import com.jlife.abon.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyFacade {
    String DEFAULT_USER_LOGO_PATH = "/images/default/user_profile.jpg";

    CompanyData getCompanyWithActiveProducts(String companyId);

    /**
     *
     * @param card
     * @param companyId
     * @return
     *
     * @deprecated please use {@link #createClient(ClientData, String)}
     */
    @Deprecated
    CardData bindCard(CardData card, String companyId);

    ClientData createClient(ClientData clientData, String companyId);

    ClientData archiveClient(Long cardUUID, String companyId);

    ClientData restoreClient(Long cardUUID, String companyId);

    Page<ClientData> findClients(String companyId, Pageable pageable);

    CompanyData updateCompanyOwn(String companyId, CompanyData company);

    CompanyData updateCompanyRequisitesOwn(String companyId, CompanyRequisitesData companyRequisites);

    CompanyData updateCompanySettings(String companyId, CompanySettingsData companySettings);

    ClientData updateClientByUserId(String userId, ClientData user, String companyId);

    ClientData updateClientByCardUUID(Long cardUUID, ClientData client, String companyId);

    List<CompanyData> getAllCompaniesWithProducts();


    List<CompanyData> getAllCompanies();

    List<CompanyData> getAllPublishedCompanies();

    CompanyData getCompany(String companyId);

    CompanyData getCompanyWithActivePublishedProducts(String companyId);

    CompanyData updateCompanyProductLogoPathSettings(String productLogoPath, String companyId);

    void acceptAgreementAndPublicOffer(String companyId);

    boolean checkCompanyIsAcceptedAgreement(String companyId);

    boolean checkCompanyIsAcceptedOffer(String companyId);

    CompanyData saveRaw(CompanyData rowCompany);

    ClientData save(ClientData clientData);

    CompanyData setCurrentTariffAsCompany(int countOfMonth, String tariffId, String companyId);

    List<CompanyData> getAllCompaniesWithAdmins();

}

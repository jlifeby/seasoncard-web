package com.jlife.abon.service;

import com.google.common.collect.Collections2;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.CompanySettings;
import com.jlife.abon.entity.Tariff;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.CompanyRepository;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyService {

    @Autowired
    private SCConfigurationService scConfigurationService;

    @Autowired
    private CompanyRepository companyRepository;


    @Value("${app.offerVersion}")
    private String offerVersion;

    @Value("${app.agreementVersion}")
    private String agreementVersion;

    @Autowired
    private TariffService tariffService;

    @NotNull
    public Company getCompany(String companyId) {
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new ResourceNotFoundException(ApiErrorCode.COMPANY_NOT_FOUND, companyId);
        }
        return company;
    }

    public List<Company> getAllPublishedCompanies() {
        return companyRepository.findByPublished(true);
    }

    public Collection<String> getAllPublishedCompanyIds() {
        List<Company> publishedCompanies = this.getAllPublishedCompanies();
        Collection<String> publishedCompanyIds = Collections2.transform(publishedCompanies, input -> input.getId());
        return publishedCompanyIds;
    }

    public Company updateDefaultProductLogo(String productLogoPath, String companyId) {
        Company company = getCompany(companyId);
        company.setDefaultProductLogoPath(productLogoPath);
        return companyRepository.save(company);
    }

    public void acceptAgreementAndPublicOffer(String companyId) {
        Company company = getCompany(companyId);
        List<String> acceptedAgreements = company.getAcceptedAgreements();
        List<String> acceptedPublicOffers = company.getAcceptedPublicOffers();
        if (company.getCurrentTariff() == null) {
            Tariff freeTariff = tariffService.prepareFreeTariff(companyId);
            company.setCurrentTariff(freeTariff);
        }
        if (company.getContractDate() == null) {
            company.setContractDate(new DateTime());
        }
        if (!acceptedAgreements.contains(agreementVersion)) {
            acceptedAgreements.add(agreementVersion);
            company.setAcceptedAgreements(acceptedAgreements);
        }
        if (!acceptedPublicOffers.contains(offerVersion)) {
            acceptedPublicOffers.add(offerVersion);
            company.setAcceptedPublicOffers(acceptedPublicOffers);
        }
        companyRepository.save(company);
    }

    public boolean checkCompanyIsAcceptedAgreement(String companyId) {
        Company company = getCompany(companyId);
        return company.checkIsAcceptedAgreementVersion(agreementVersion);
    }

    public boolean checkCompanyIsAcceptedOffer(String companyId) {
        Company company = getCompany(companyId);
        return company.checkIsAcceptedPublicOfferVersion(offerVersion);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company updateCompanySettings(String companyId, CompanySettings companySettings) {
        Company company = getCompany(companyId);
        CompanySettings existedSettings = company.getCompanySettings();
        existedSettings.setNotificationEmail(companySettings.getNotificationEmail());
        existedSettings.setAbonSoldEmailNotification(companySettings.isAbonSoldEmailNotification());
        return companyRepository.save(company);
    }
}

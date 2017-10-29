package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlife.abon.enumeration.CompanyCategory;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import com.jlife.abon.utility.DefaultValues;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * It's object that has services(subscription, season tickets)
 *
 * @author Dzmitry Misiuk
 */
@Document
public class Company extends Entity {

    private String name;
    private String description;
    private Address address;
    private String email;
    private String site;
    private List<String> phones;
    private List<SocialNetwork> socialNetworks;
    private List<WorkingHoursEntry> workingHours;
    private List<CompanyCategory> categories;
    private Country country;

    @Deprecated
    private String logoPath;
    private Media logoMedia;
    private String logoMediaId;

    private boolean published;

    @Transient
    private List<Product> products;

    private String defaultProductLogoPath;

    private List<String> acceptedPublicOffers;
    private List<String> acceptedAgreements;

    @Indexed(unique = true, sparse = true)
    private int contractId;
    private DateTime contractDate;

    @JsonIgnore
    private CompanyRequisites companyRequisites;
    @JsonIgnore
    private Tariff currentTariff;
    @JsonIgnore
    private List<Tariff> previousTariffs;
    @JsonIgnore
    private CompanySettings companySettings;

    public Company() {
        this.socialNetworks = new ArrayList<>();
        this.workingHours = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.phones = new ArrayList<>();
        this.published = true;
        this.defaultProductLogoPath = DefaultValues.DEFAULT_PRODUCT_IMAGE_PATH;
        this.acceptedPublicOffers = new ArrayList<>();
        this.acceptedAgreements = new ArrayList<>();
        this.companyRequisites = new CompanyRequisites();
        this.companySettings = new CompanySettings();
        this.previousTariffs = new ArrayList<>();
        this.country = Country.BELARUS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public List<WorkingHoursEntry> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursEntry> workingHours) {
        this.workingHours = workingHours;
    }

    public List<CompanyCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<CompanyCategory> categories) {
        this.categories = categories;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Media getLogoMedia() {
        return logoMedia;
    }

    public void setLogoMedia(Media logoMedia) {
        this.logoMedia = logoMedia;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDefaultProductLogoPath() {
        return defaultProductLogoPath;
    }

    public void setDefaultProductLogoPath(String defaultProductLogoPath) {
        this.defaultProductLogoPath = defaultProductLogoPath;
    }

    public List<String> getAcceptedPublicOffers() {
        return acceptedPublicOffers;
    }

    public void setAcceptedPublicOffers(List<String> acceptedPublicOffers) {
        this.acceptedPublicOffers = acceptedPublicOffers;
    }

    public List<String> getAcceptedAgreements() {
        return acceptedAgreements;
    }

    public void setAcceptedAgreements(List<String> acceptedAgreements) {
        this.acceptedAgreements = acceptedAgreements;
    }

    public boolean checkIsAcceptedPublicOfferVersion(String version) {
        return this.acceptedPublicOffers.contains(version);
    }

    public boolean checkIsAcceptedAgreementVersion(String version) {
        return this.acceptedPublicOffers.contains(version);
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public DateTime getContractDate() {
        return contractDate;
    }

    public void setContractDate(DateTime contractDate) {
        this.contractDate = contractDate;
    }

    public CompanyRequisites getCompanyRequisites() {
        return companyRequisites;
    }

    public void setCompanyRequisites(CompanyRequisites companyRequisites) {
        this.companyRequisites = companyRequisites;
    }

    public Tariff getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(Tariff currentTariff) {
        this.currentTariff = currentTariff;
    }

    public List<Tariff> getPreviousTariffs() {
        return previousTariffs;
    }

    public void setPreviousTariffs(List<Tariff> previousTariffs) {
        this.previousTariffs = previousTariffs;
    }

    public CompanySettings getCompanySettings() {
        return companySettings;
    }

    public void setCompanySettings(CompanySettings companySettings) {
        this.companySettings = companySettings;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean canSendEmail() {
        return getCurrentTariff() != null && !getCurrentTariff().isFree();
    }

    public boolean canSendSms() {
        return getCurrentTariff() != null && !getCurrentTariff().isFree();
    }

    public Currency getCurrency() {
        switch (getCountry()) {
            case BELARUS:
                return Currency.BYN;
            case RUSSIA:
                return Currency.RUB;
            default:
                return Currency.USD;
        }
    }
}

package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlife.abon.enumeration.CompanyCategory;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import com.jlife.abon.utility.DefaultValues;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * It's object that has services(subscription, season tickets)
 *
 * @author Dzmitry Misiuk
 */
public class CompanyData extends BaseData {

    private String name;
    private String description;
    private AddressData address;
    private String email;
    private String site;
    private List<String> phones;
    private List<SocialNetworkData> socialNetworks;
    private List<WorkingHoursEntryData> workingHours;
    private List<CompanyCategory> categories;
    private Country country;

    @Deprecated
    private String logoPath;
    private MediaData logoMedia;
    private String logoMediaId;

    private boolean published;

    private List<ProductData> products;

    private String defaultProductLogoPath;

    private List<String> acceptedPublicOffers;
    private List<String> acceptedAgreements;

    private int contractId;
    private DateTime contractDate;

    @JsonIgnore
    private CompanyRequisitesData companyRequisites;
    @JsonIgnore
    private TariffData currentTariff;
    @JsonIgnore
    private List<TariffData> previousTariffs;
    @JsonIgnore
    private CompanySettingsData companySettings;

    private List<UserData> administrators;

    public CompanyData() {
        this.socialNetworks = new ArrayList<>();
        this.workingHours = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.phones = new ArrayList<>();
        this.published = true;
        this.defaultProductLogoPath = DefaultValues.DEFAULT_PRODUCT_IMAGE_PATH;
        this.acceptedPublicOffers = new ArrayList<>();
        this.acceptedAgreements = new ArrayList<>();
        this.companyRequisites = new CompanyRequisitesData();
        this.previousTariffs = new ArrayList<>();
        this.companySettings = new CompanySettingsData();
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

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
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

    public List<SocialNetworkData> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<SocialNetworkData> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public List<WorkingHoursEntryData> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursEntryData> workingHours) {
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

    public MediaData getLogoMedia() {
        return logoMedia;
    }

    public void setLogoMedia(MediaData logoMedia) {
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

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
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

    public CompanyRequisitesData getCompanyRequisites() {
        return companyRequisites;
    }

    public void setCompanyRequisites(CompanyRequisitesData companyRequisites) {
        this.companyRequisites = companyRequisites;
    }

    public TariffData getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(TariffData currentTariff) {
        this.currentTariff = currentTariff;
    }

    public List<TariffData> getPreviousTariffs() {
        return previousTariffs;
    }

    public void setPreviousTariffs(List<TariffData> previousTariffs) {
        this.previousTariffs = previousTariffs;
    }

    public CompanySettingsData getCompanySettings() {
        return companySettings;
    }

    public void setCompanySettings(CompanySettingsData companySettings) {
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

    public List<UserData> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<UserData> administrators) {
        this.administrators = administrators;
    }

    public UserData getFirstAdmin() {
        return this.administrators != null && !this.administrators.isEmpty() ? this.administrators.get(0) : null;
    }

    public PriceData getMonthPrice() {
        return currentTariff != null ? currentTariff.getMonthPrice(getCountry()) : null;
    }
}

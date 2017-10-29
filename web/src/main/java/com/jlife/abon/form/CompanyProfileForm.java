package com.jlife.abon.form;

import com.jlife.abon.dto.AddressData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.enumeration.Country;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class CompanyProfileForm implements Serializable {

//    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Deprecated
    @NotBlank
    private String logoPath;
    private String logoMediaId;
    @NotNull
    private AddressData address;
//    @NotBlank
    @Email
    private String email;
    private boolean published;
    @URL
    private String site;
    // todo @Pattern(regexp = Patterns.PHONE)
    private String phone1;
    // todo @Pattern(regexp = Patterns.PHONE)
    private String phone2;
    // todo @Pattern(regexp = Patterns.PHONE)
    private String phone3;
    private Country country;

    public CompanyProfileForm() {
        this.published = true;
        this.logoPath = "/images/default/company_profile.png";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
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

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public static CompanyProfileForm fromCompany(CompanyData company) {
        CompanyProfileForm form = new CompanyProfileForm();
        form.setId(company.getId());
        form.setName(company.getName());
        form.setDescription(company.getDescription());
        form.setLogoPath(company.getLogoPath());
        form.setLogoMediaId(company.getLogoMediaId());
        form.setAddress(company.getAddress());
        form.setEmail(company.getEmail());
        form.setSite(company.getSite());
        form.setPublished(company.isPublished());
        int phoneSize = company.getPhones().size();
        if (phoneSize > 1) {
            form.setPhone1(company.getPhones().get(0));
        }
        if (phoneSize > 2) {
            form.setPhone2(company.getPhones().get(1));
        }
        if (phoneSize > 3) {
            form.setPhone3(company.getPhones().get(2));
        }
        form.setCountry(company.getCountry());
        return form;
    }

    public CompanyData toCompany() {
        CompanyData company = new CompanyData();
        company.setId(getId());
        company.setName(getName());
        company.setDescription(getDescription());
        company.setSite(getSite());
        company.setEmail(getEmail());
        company.setAddress(getAddress());
        company.setLogoPath(getLogoPath());
        company.setLogoMediaId(getLogoMediaId());
        company.setPublished(isPublished());
        List<String> phones = new ArrayList<>();
        phones.add(getPhone1());
        phones.add(getPhone2());
        phones.add(getPhone3());
        company.setPhones(phones);
        company.setCountry(this.country);
        return company;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean checkIsNew() {
        return id == null;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

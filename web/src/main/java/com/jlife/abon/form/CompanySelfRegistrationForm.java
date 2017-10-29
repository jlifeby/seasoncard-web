package com.jlife.abon.form;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.validator.Patterns;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class CompanySelfRegistrationForm implements Serializable {

    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String adminEmail;
    @NotBlank
    private String adminName;
    @NotBlank
    private String adminLastName;
    @NotBlank
    @Pattern(regexp = Patterns.PHONE)
    private String adminPhone;
    @NotNull
    private Country country;

    public CompanySelfRegistrationForm() {
        this.country = Country.RUSSIA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    public CompanyData toCompany() {
        CompanyData company = new CompanyData();
        company.setName(this.getName());
        company.setCountry(this.country);
        company.setLogoPath("/images/default/company_profile.png");
        return company;
    }

    public UserData extractUser() {
        UserData user = new UserData();
        user.setEmail(getAdminEmail());
        user.setName(getAdminName());
        user.setLastName(getAdminLastName());
        user.setCountry(getCountry());
        user.setLogoPath("/images/default/user_profile.jpg");
        user.setPhone(getAdminPhone());
        return user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }
}

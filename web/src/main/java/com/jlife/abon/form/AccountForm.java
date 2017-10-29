package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.validator.Patterns;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class AccountForm implements Serializable {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime birthday;
    @Pattern(regexp = Patterns.PHONE)
    private String phone;
    @NotBlank
    private String logoPath;
    private String logoMediaId;
    private String vehicleRegistrationPlate;
    private Country country;

    public AccountForm() {
        this.logoPath = "/images/default/user_profile.jpg";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserData toUser() {
        UserData user = new UserData();
        user.setId(id);
        user.setLogoPath(logoPath);
        user.setLogoMediaId(getLogoMediaId());
        DateTime birthday = getBirthday();
        user.setBirthday(new DateTime(birthday.getYear(), birthday.getMonthOfYear(), birthday.getDayOfMonth(), 0, 0, 0, DateTimeZone.UTC));
        user.setName(getName());
        user.setLastName(getLastName());
        user.setPhone(getPhone());
        user.setVehicleRegistrationPlate(getVehicleRegistrationPlate());
        user.setCountry(getCountry());
        return user;
    }

    public static AccountForm fromUser(UserData user) {
        AccountForm accountForm = new AccountForm();
        accountForm.setId(user.getId());
        accountForm.setName(user.getName());
        accountForm.setLastName(user.getLastName());
        accountForm.setLogoPath(user.getLogoPath());
        accountForm.setLogoMediaId(user.getLogoMediaId());
        accountForm.setBirthday(user.getBirthday());
        accountForm.setPhone(user.getPhone());
        accountForm.setVehicleRegistrationPlate(user.getVehicleRegistrationPlate());
        accountForm.setCountry(user.getCountry());
        return accountForm;
    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}

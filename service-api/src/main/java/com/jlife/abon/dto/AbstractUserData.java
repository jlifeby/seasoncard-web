package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.Country;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Dzmitry Misiuk
 */
public class AbstractUserData extends BaseData {

    private String name;
    private String lastName;
    private String middleName;

    private String phone;
    private Country country = Country.BELARUS;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime birthday;

    private String logoPath;
    private MediaData logoMedia;
    private String logoMediaId;

    private String vehicleRegistrationPlate;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
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

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getDaysBeforeBirthday() {
        if (getBirthday() != null) {
            DateTime now = DateTime.now().withTimeAtStartOfDay();
            DateTime birthdayCurrentYear = getBirthday().withYear(now.getYear()).withTimeAtStartOfDay();

            if (birthdayCurrentYear.isBefore(now)) {
                birthdayCurrentYear = birthdayCurrentYear.withYear(now.getYear() + 1);
                return Days.daysBetween(now, birthdayCurrentYear).getDays();
            } else {
                return Days.daysBetween(now, birthdayCurrentYear).getDays();
            }
        } else {
            return 999;
        }

    }
}

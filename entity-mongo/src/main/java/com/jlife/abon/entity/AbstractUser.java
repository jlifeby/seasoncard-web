package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.Country;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Dzmitry Misiuk
 */
public class AbstractUser extends Entity {

    private String name;
    private String lastName;
    private String middleName;

    @Indexed(unique = false)
    private String phone;
    private Country country = Country.BELARUS;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime birthday;

    private String logoPath;
    private Media logoMedia;
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

    public DateTime getNextBirthdayDate() {
        final DateTime now = DateTime.now().withTimeAtStartOfDay().withZoneRetainFields(DateTimeZone.UTC);
        return getNextBirthdayDate(now);
    }

    public DateTime getNextBirthdayDate(DateTime fromDate) {
        DateTime birthday = getBirthday();
        if (birthday != null) {
            birthday = birthday.withZone(DateTimeZone.UTC);
            Integer yearOffset = 0;
            final DateTime then = fromDate.withYear(birthday.getYear());
            final boolean is29FebCase = birthday.getDayOfMonth() == 28 && birthday.getMonthOfYear() == 2 && fromDate.getDayOfMonth() != then.getDayOfMonth();
            if (birthday.isBefore(then) || is29FebCase) {
                yearOffset += 1;
            }
            return birthday.withYear(fromDate.getYear() + yearOffset);
        } else {
            return null;
        }
    }

    public int getDaysBeforeBirthday() {
        final DateTime nextBirthday = getNextBirthdayDate();
        if (nextBirthday != null) {
            return Days.daysBetween(DateTime.now().withTimeAtStartOfDay().withZoneRetainFields(DateTimeZone.UTC), nextBirthday).getDays();
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public boolean isNextBirthdayWithinDateRange(DateTime startDate, DateTime endDate) {
        final DateTime nextBirthdayDate = getNextBirthdayDate(startDate);
        if (nextBirthdayDate != null) {
            final boolean before = startDate.minusDays(1).isBefore(nextBirthdayDate);
            final boolean after = endDate.withTimeAtStartOfDay().plusDays(1).isAfter(nextBirthdayDate);
            return before && after;
        } else {
            return false;
        }
    }
}

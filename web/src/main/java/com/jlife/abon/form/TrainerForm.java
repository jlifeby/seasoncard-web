package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.TrainerData;
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
 * @author Dzmitry Khralovich
 */
public class TrainerForm implements Serializable {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private String middleName;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime birthday;
    @Pattern(regexp = Patterns.PHONE)
    private String phone;
    @NotBlank
    private String logoPath;
    private String logoMediaId;
    private Country country;
    @NotBlank
    private String description;
    private String htmlContent;
    private boolean published;

    public TrainerForm() {
        this.logoPath = "/images/default/user_profile.jpg";
        this.published = true;
    }

    public TrainerForm(TrainerData trainer) {
        this.setId(trainer.getId());
        this.setName(trainer.getName());
        this.setLastName(trainer.getLastName());
        this.setMiddleName(trainer.getMiddleName());
        this.setLogoPath(trainer.getLogoPath());
        this.setLogoMediaId(trainer.getLogoMediaId());
        this.setBirthday(trainer.getBirthday());
        this.setPhone(trainer.getPhone());
        this.setCountry(trainer.getCountry());
        this.setDescription(trainer.getDescription());
        this.setHtmlContent(trainer.getHtmlContent());
        this.setPublished(trainer.isPublished());
    }

    public TrainerData toTrainer() {
        TrainerData trainer = new TrainerData();
        trainer.setId(id);
        trainer.setName(getName());
        trainer.setLastName(getLastName());
        trainer.setMiddleName(getMiddleName());
        trainer.setLogoPath(logoPath);
        trainer.setLogoMediaId(getLogoMediaId());
        DateTime birthday = getBirthday();
        trainer.setBirthday(new DateTime(birthday.getYear(), birthday.getMonthOfYear(), birthday.getDayOfMonth(), 0, 0, 0, DateTimeZone.UTC));
        trainer.setPhone(getPhone());
        trainer.setCountry(getCountry());
        trainer.setDescription(getDescription());
        trainer.setHtmlContent(getHtmlContent());
        trainer.setPublished(isPublished());
        return trainer;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}

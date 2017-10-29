package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.validator.Patterns;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
public class BindClientForm implements Serializable {

    private String id;
    private String userId;

    @NotBlank
    private String logoPath;
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
    private String vehicleRegistrationPlate;
    @NotNull
    private Long cardUUID;

    private String logoMediaId;
    //private Country country;

    private String comment;
    private String tagsAsString;

    private String internalId;
    private Boolean active;

    public BindClientForm() {
        this.logoPath = "/images/default/user_profile.jpg";
    }

    public BindClientForm(ClientData clientData) {

        setId(clientData.getId());
        setUserId(clientData.getUserId());
        setCardUUID(clientData.getCardUUID());
        setLogoPath(clientData.getLogoPath());
        setLogoMediaId(clientData.getLogoMediaId());
        //TODO check Birthday
        setBirthday(clientData.getBirthday());
        setName(clientData.getName());
        setLastName(clientData.getLastName());
        setPhone(clientData.getPhone());
        setVehicleRegistrationPlate(clientData.getVehicleRegistrationPlate());
        setInternalId(clientData.getInternalId());
        setTagsAsString(StringUtils.join(clientData.getTags(), ','));
        setComment(clientData.getComment());
        setActive(clientData.isActive());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
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

    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(Long cardUUID) {
        this.cardUUID = cardUUID;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }

    public ClientData toClientData() {
        ClientData clientData = new ClientData();
        clientData.setId(getId());
        clientData.setUserId(getUserId());
        clientData.setCardUUID(getCardUUID());
        clientData.setName(getName());
        clientData.setLastName(getLastName());
        clientData.setPhone(getPhone());
        DateTime birthday = getBirthday();
        clientData.setBirthday(new DateTime(birthday.getYear(), birthday.getMonthOfYear(), birthday.getDayOfMonth(), 0, 0, 0, DateTimeZone.UTC));
        clientData.setLogoPath(getLogoPath());
        clientData.setLogoMediaId(getLogoMediaId());
        clientData.setVehicleRegistrationPlate(getVehicleRegistrationPlate());
        if (StringUtils.isNoneBlank(getInternalId())){
            clientData.setInternalId(getInternalId().trim());
        } else {
            clientData.setInternalId("");
        }

        Stream<String> tags = Arrays.stream(getTagsAsString().split(","));
        clientData.setTags(tags.map(String::trim).filter(StringUtils::isNoneBlank).collect(Collectors.toList()));

        clientData.setComment(getComment());
        return clientData;
    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public boolean checkIsNew() {
        return id == null;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

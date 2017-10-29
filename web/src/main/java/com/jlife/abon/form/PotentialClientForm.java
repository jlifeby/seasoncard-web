package com.jlife.abon.form;

import com.jlife.abon.dto.ClientData;
import com.jlife.abon.validator.Patterns;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class PotentialClientForm implements Serializable {

    public static final String LOGO_PATH = "/images/default/user_profile.jpg";

    @NotBlank
    private String name;
    private String lastName;
    @NotNull
    @Pattern(regexp = Patterns.PHONE)
    private String phone;
    @Email
    private String email;
    private String comment;

    public PotentialClientForm() {

    }

    public ClientData toClientData() {
        ClientData clientData = new ClientData();
        clientData.setName(getName());
        clientData.setLastName(getLastName());
        clientData.setPhone(getPhone());
        clientData.setEmail(getEmail());
        clientData.setComment(getComment());
        clientData.setLogoPath(LOGO_PATH);
        return clientData;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

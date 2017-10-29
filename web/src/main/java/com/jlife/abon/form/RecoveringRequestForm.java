package com.jlife.abon.form;

import com.jlife.abon.validator.Patterns;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;

/**
 * Form when we user type your email to recover access to system.
 *
 * @author Dzmitry Misiuk
 */
public class RecoveringRequestForm {

    //@NotBlank
    @Email
    private String email;

    //@NotBlank
    @Pattern(regexp = Patterns.PHONE)
    private String phone;

    private Long cardUUID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}

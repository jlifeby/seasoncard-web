package com.jlife.abon.form.property;

import com.jlife.abon.validator.Patterns;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class PhoneForm implements Serializable {

    @Pattern(regexp = Patterns.PHONE)
    @NotNull
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

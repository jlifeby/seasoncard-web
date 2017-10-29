package com.jlife.abon.form;

import com.jlife.abon.validator.ComplexPassword;
import com.jlife.abon.validator.FieldMatch;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author Dzmitry Misiuk
 */
@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match"),
})
public class ChangingPasswordForm {

    @NotBlank
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 25)
    @ComplexPassword
    private String newPassword;
    private String confirmNewPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}

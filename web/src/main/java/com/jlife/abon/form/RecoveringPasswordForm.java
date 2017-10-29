package com.jlife.abon.form;

import com.jlife.abon.validator.ComplexPassword;
import com.jlife.abon.validator.FieldMatch;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Dzmitry Misiuk
 */
@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match"),
})
public class RecoveringPasswordForm {

    @NotBlank
    private String recoveringId;
    @NotBlank
    @ComplexPassword
    private String newPassword;
    private String confirmNewPassword;

    public String getRecoveringId() {
        return recoveringId;
    }

    public void setRecoveringId(String recoveringId) {
        this.recoveringId = recoveringId;
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

package com.jlife.abon.form;

import com.jlife.abon.dto.UserData;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class CompanyAdminForm implements Serializable {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserData toUser() {
        UserData user = new UserData();
        user.setId(id);
        user.setName(getName());
        user.setLastName(getLastName());
        user.setEmail(getEmail());
        return user;
    }

    public static CompanyAdminForm fromUser(UserData user) {
        CompanyAdminForm companyAdminForm = new CompanyAdminForm();
        companyAdminForm.setId(user.getId());
        companyAdminForm.setName(user.getName());
        companyAdminForm.setLastName(user.getLastName());
        companyAdminForm.setEmail(user.getEmail());
        return companyAdminForm;
    }

}

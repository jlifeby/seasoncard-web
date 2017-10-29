package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.enumeration.UserState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dzmitry Misiuk
 */
public class UserData extends AbstractUserData {

    public boolean potential;
    private String email;
    private String newEmail;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private List<String> roles = new ArrayList<>();
    @JsonIgnore
    private String confirm;
    @JsonIgnore
    private String status;
    private Long cardUUID;
    private Set<UserState> userStates;
    @JsonIgnore
    private String createdByCompany;
    private CompanyData company;
    private List<AbonnementData> abonnements;
    @JsonIgnore
    private String recoveringId;
    private boolean allowedSmsReceiving;
    private boolean allowedEmailReceiving;
    private List<PhoneChangingData> phoneChangings;

    public UserData() {
        this.roles = new ArrayList<>();
        this.userStates = new HashSet<>();
        this.allowedEmailReceiving = true;
        this.allowedSmsReceiving = true;
        this.phoneChangings = new ArrayList<>();
    }

    // todo make this list immutable
    public List<String> getRoles() {
        return roles;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CompanyData getCompany() {
        return company;
    }

    public void setCompany(CompanyData company) {
        this.company = company;
    }

    public List<AbonnementData> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<AbonnementData> abonnements) {
        this.abonnements = abonnements;
    }

    public String getCreatedByCompany() {
        return createdByCompany;
    }

    public void setCreatedByCompany(String createdByCompany) {
        this.createdByCompany = createdByCompany;
    }

    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(Long cardUUID) {
        this.cardUUID = cardUUID;
    }

    public Set<UserState> getUserStates() {
        return userStates;
    }

    public void setUserStates(Set<UserState> userStates) {
        this.userStates = userStates;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getRecoveringId() {
        return recoveringId;
    }

    public void setRecoveringId(String recoveringId) {
        this.recoveringId = recoveringId;
    }

    public boolean hasAlreadyConfirmedEmail() {
        return this.userStates.contains(UserState.CONFIRMED_EMAIL);
    }

    public boolean isWaitingToConfirmEmail() {
        return this.userStates.contains(UserState.WAITING_FOR_CONFIRM_EMAIL);
    }

    public boolean isChangedInitPassword() {
        return this.userStates.contains(UserState.CHANGED_INIT_PASSWORD);
    }

    public boolean isAcceptedAgreement() {
        return this.userStates.contains(UserState.ACCEPTED_AGREEMENT);
    }

    public boolean hasUserRole() {
        return this.roles.contains(Role.ROLE_USER.name());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllowedSmsReceiving() {
        return allowedSmsReceiving;
    }

    public void setAllowedSmsReceiving(boolean allowedSmsReceiving) {
        this.allowedSmsReceiving = allowedSmsReceiving;
    }

    public boolean isAllowedEmailReceiving() {
        return allowedEmailReceiving;
    }

    public void setAllowedEmailReceiving(boolean allowedEmailReceiving) {
        this.allowedEmailReceiving = allowedEmailReceiving;
    }

    public boolean isPotential() {
        return potential;
    }

    public void setPotential(boolean potential) {
        this.potential = potential;
    }

    public List<PhoneChangingData> getPhoneChangings() {
        return phoneChangings;
    }

    public void setPhoneChangings(List<PhoneChangingData> phoneChangings) {
        this.phoneChangings = phoneChangings;
    }

    public boolean hasAnyEmail() {
        String email = this.email;
        if (email == null) {
            email = this.newEmail;
        }
        return email != null;
    }

    public String getFullName() {
        return String.format(
                "%s %s %s",
                getName() != null ? getName() : "",
                getLastName() != null ? getLastName() : "",
                getMiddleName() != null ? getMiddleName() : ""
        );
    }

    @Override
    public String toString() {
        return String.format("UserData(email=%s, name=%s)", email, getFullName());
    }

}


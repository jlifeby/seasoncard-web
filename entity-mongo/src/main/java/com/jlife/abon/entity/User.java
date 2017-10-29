package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.enumeration.UserState;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "users")
public class User extends AbstractUser {

    @Indexed(unique = true, sparse = true)
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

    @Indexed(unique = true, sparse = true)
    private Long cardUUID;

    private Set<UserState> userStates;

    @JsonIgnore
    private String createdByCompany;

    @Transient
    private Company company;

    @Transient
    private List<Abonnement> abonnements;

    @JsonIgnore
    private String recoveringId;

    private List<PhoneChanging> phoneChangings;

    private boolean allowedSmsReceiving;

    private boolean allowedEmailReceiving;
    private boolean potential;

    public User() {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
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

    public List<PhoneChanging> getPhoneChangings() {
        return phoneChangings;
    }

    public void setPhoneChangings(List<PhoneChanging> phoneChangings) {
        this.phoneChangings = phoneChangings;
    }

    public boolean hasAnyEmail() {
        String email = this.email;
        if (email == null) {
            email = this.newEmail;
        }
        return email != null;
    }

    public boolean isPotential() {
        return potential;
    }

    public void setPotential(boolean potential) {
        this.potential = potential;
    }
}


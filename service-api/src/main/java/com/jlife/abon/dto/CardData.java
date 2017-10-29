package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class CardData extends BaseData {

    private String userId;
    private Long cardUUID;
    private String nfcUUID;
    private String initializingCompany;
    @JsonIgnore
    private String deliveredToCompany;
    @JsonIgnore
    private boolean free;
    @Deprecated
    private UserData user;
    private ClientData client;
    private List<AbonnementData> abonnements;
    private String comment;

    @JsonIgnore
    private String hashedPassword;
    private List<AbonnementData> lastAbonnements;

    private String previousUserId;

    public CardData() {
        this.abonnements = new ArrayList<>();
        this.lastAbonnements = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(Long cardUUID) {
        this.cardUUID = cardUUID;
    }

    public String getInitializingCompany() {
        return initializingCompany;
    }

    public void setInitializingCompany(String initializingCompany) {
        this.initializingCompany = initializingCompany;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public List<AbonnementData> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<AbonnementData> abonnements) {
        this.abonnements = abonnements;
    }

    public String getNfcUUID() {
        return nfcUUID;
    }

    public void setNfcUUID(String nfcUUID) {
        this.nfcUUID = nfcUUID;
    }

    public String getDeliveredToCompany() {
        return deliveredToCompany;
    }

    public void setDeliveredToCompany(String deliveredToCompany) {
        this.deliveredToCompany = deliveredToCompany;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setLastAbonnements(List<AbonnementData> lastAbonnements) {
        this.lastAbonnements = lastAbonnements;
    }

    public List<AbonnementData> getLastAbonnements() {
        return lastAbonnements;
    }

    public ClientData getClient() {
        return client;
    }

    public void setClient(ClientData client) {
        this.client = client;
    }

    public String getPreviousUserId() {
        return previousUserId;
    }

    public void setPreviousUserId(String previousUserId) {
        this.previousUserId = previousUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

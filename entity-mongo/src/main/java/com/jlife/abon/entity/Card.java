package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Document
public class Card extends Entity {

    @Indexed(unique = true, sparse = true)
    private String userId;
    @Indexed(unique = true)
    private Long cardUUID;
    @Indexed(unique = true, sparse = true)
    private String nfcUUID;
    private String initializingCompany;
    @JsonIgnore
    private String deliveredToCompany;
    @JsonIgnore
    private boolean free;
    @Transient
    private User user;
    @Transient
    private Client client;
    @Transient
    private List<Abonnement> abonnements;

    @JsonIgnore
    private String hashedPassword;
    @Transient
    private List<Abonnement> lastAbonnements;

    private String previousUserId;

    public Card() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
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

    public void setLastAbonnements(List<Abonnement> lastAbonnements) {
        this.lastAbonnements = lastAbonnements;
    }

    public List<Abonnement> getLastAbonnements() {
        return lastAbonnements;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPreviousUserId() {
        return previousUserId;
    }

    public void setPreviousUserId(String previousUserId) {
        this.previousUserId = previousUserId;
    }

    public String getComment() {
        if (client != null) {
            return client.getComment();
        } else {
            return null;
        }
    }
}

package com.jlife.abon.entity;

import kotlin.jvm.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "clients", language = "ru")
@CompoundIndexes({
        @CompoundIndex(unique = true, def = "{'userId' : 1, 'companyId' : 1}")
})
/**
 * !!!
 * Also there is index
 *
 * db.clients.ensureIndex({companyId : 1, internalId : 1}, { unique: true, partialFilterExpression: { internalId: { $exists: true } } })
 */
public class Client extends AbstractUser {

    private String userId;
    private Long cardUUID;
    private String comment;
    private List<String> tags;
    private String internalId;
    @Transient
    private String email;
    private boolean potential;


    public Client() {
        this.tags = new ArrayList<>();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPotential() {
        return potential;
    }

    public void setPotential(boolean potential) {
        this.potential = potential;
    }
}

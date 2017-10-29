package com.jlife.abon.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class ClientData extends AbstractUserData {

    public String email;
    private String userId;
    private Long cardUUID;
    private String comment;
    private List<String> tags;
    private String internalId;
    private UserData user;
    private boolean potential;

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

    public void addTag(String tag) {
        if(this.tags == null){
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
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

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientData(");
        sb.append("userId=").append(userId);
        sb.append(",cardUUID=").append(cardUUID);
        sb.append(",comment=").append(comment);
        sb.append(",tags=").append(tags);
        sb.append(",internalId=").append(internalId);
        sb.append(",user=").append(user);
        sb.append(")");
        return sb.toString();
    }
}

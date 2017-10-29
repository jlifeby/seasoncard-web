package com.jlife.abon.aggregation;

/**
 * @author Dzmitry Misiuk
 */
public class FindingClientResult {

    private String _id;
    private String fullNameWithCard;
    private long cardUUID;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullNameWithCard() {
        return fullNameWithCard;
    }

    public void setFullNameWithCard(String fullNameWithCard) {
        this.fullNameWithCard = fullNameWithCard;
    }

    public long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(long cardUUID) {
        this.cardUUID = cardUUID;
    }
}

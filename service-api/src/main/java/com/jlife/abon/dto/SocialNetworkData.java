package com.jlife.abon.dto;

import com.jlife.abon.enumeration.SocialNetworkType;

/**
 * @author Dzmitry Misiuk
 */
public class SocialNetworkData extends IdData {

    private SocialNetworkType type;
    private String url;

    public SocialNetworkType getType() {
        return type;
    }

    public void setType(SocialNetworkType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

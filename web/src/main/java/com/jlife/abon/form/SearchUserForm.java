package com.jlife.abon.form;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class SearchUserForm implements Serializable {

    private String name;
    private String abonId;

    public SearchUserForm() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbonId() {
        return abonId;
    }

    public void setAbonId(String abonId) {
        this.abonId = abonId;
    }
}

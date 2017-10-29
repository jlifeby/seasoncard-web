package com.jlife.abon.dto;

import java.io.Serializable;

/**
 * Base EntityData. Includes only id field.
 *
 * @author Dzmitry Misiuk
 */
public abstract class IdData implements Serializable {

    private String id;

    protected IdData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean checkIsNew() {
        return id == null;
    }
}

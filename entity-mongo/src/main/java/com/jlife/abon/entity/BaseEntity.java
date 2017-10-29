package com.jlife.abon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Base Entity. Includes only id field.
 *
 * @author Dzmitry Misiuk
 */
public abstract class BaseEntity implements Serializable, Persistable<String> {

    @Id()
    private String id;

    protected BaseEntity() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean checkIsNew() {
        return id == null;
    }

    @Override
    public boolean isNew() {
        return checkIsNew();
    }
}

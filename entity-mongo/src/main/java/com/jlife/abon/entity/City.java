package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Khralovich Dzmitry
 */
@Document
public class City extends Entity {

    private String name;
    private String path;

    public City() {
    }

    public City(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

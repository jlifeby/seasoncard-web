package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "categories")
public class Category extends Entity {

    private String name;
    private String description;
    private String path;
    private String icon;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "questionGroups")
public class QuestionGroup extends Entity {

    String name;

    public QuestionGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionGroup that = (QuestionGroup) o;

        if (this.getId() != null ? !this.getId().equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}

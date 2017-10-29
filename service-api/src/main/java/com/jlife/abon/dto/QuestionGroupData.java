package com.jlife.abon.dto;

/**
 * @author Dzmitry Misiuk
 */
public class QuestionGroupData extends BaseData {

    String name;

    public QuestionGroupData() {
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

        QuestionGroupData that = (QuestionGroupData) o;

        if (this.getId() != null ? !this.getId().equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}

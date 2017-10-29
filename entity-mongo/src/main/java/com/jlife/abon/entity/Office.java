package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "offices")
public class Office extends Entity {

    private String name;
    private String description;
    private Address address;
    private List<String> phones;
    private List<WorkingHoursEntry> workingHours;

    public Office() {
        this.phones = new ArrayList<>();
        this.workingHours = new ArrayList<>();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<WorkingHoursEntry> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursEntry> workingHours) {
        this.workingHours = workingHours;
    }
}

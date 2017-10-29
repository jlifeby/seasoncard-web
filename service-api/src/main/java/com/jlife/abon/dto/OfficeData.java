package com.jlife.abon.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class OfficeData extends BaseData {

    private String name;
    private String description;
    private AddressData address;
    private List<String> phones;
    private List<WorkingHoursEntryData> workingHours;

    public OfficeData() {
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

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<WorkingHoursEntryData> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursEntryData> workingHours) {
        this.workingHours = workingHours;
    }
}

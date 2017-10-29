package com.jlife.abon.entity;

import com.jlife.abon.enumeration.EventState;
import com.jlife.abon.enumeration.ReminderStatus;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Misiuk
 * @author Dzmitry Khralovich
 */
@Document(collection = "events", language = "russian")
public class Event extends Entity {

    private String name;
    @TextIndexed()
    private String description;
    @TextIndexed(weight=2)
    private String title;
    private String htmlContent;
    private String logo;

    private DateTime startDate;
    private DateTime endDate;

    private String city;
    private String address;
    private boolean webinar;

    private String organization;
    private String category;

    private Integer maxParticipants;

    private boolean invisible;
    private EventState state;

    private Geo geo;


    private Integer remindBefore;
    private ReminderStatus reminderStatus = ReminderStatus.PENDING;

    private String registrationDescription;

    public Event() {
        this.geo = new Geo();
        this.state = EventState.draft;
        this.invisible = false;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getRemindBefore() {
        return remindBefore;
    }

    public void setRemindBefore(Integer remindBefore) {
        this.remindBefore = remindBefore;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public void setReminderStatus(ReminderStatus reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public ReminderStatus getReminderStatus() {
        return reminderStatus;
    }

    public String getRegistrationDescription() {
        return registrationDescription;
    }

    public void setRegistrationDescription(String registrationDescription) {
        this.registrationDescription = registrationDescription;
    }

    public boolean isActual(){
        return DateTime.now().isBefore(this.startDate);
    }

    public EventState getState() {
        return state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

}

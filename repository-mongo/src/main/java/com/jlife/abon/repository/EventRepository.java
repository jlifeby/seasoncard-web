package com.jlife.abon.repository;

import com.jlife.abon.entity.Event;
import com.jlife.abon.enumeration.EventState;
import com.jlife.abon.enumeration.ReminderStatus;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.Date;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface EventRepository extends EntityRepository<Event> {

    List<Event> findByOrganization(String organization);

    List<Event> findByOrganizationAndStartDateGreaterThanOrderByStartDateAsc(String organization, Date date);

    List<Event> findByStartDateGreaterThanOrderByStartDateAsc(Date date);

    List<Event> findByReminderStatusAndStartDateBetweenOrderByStartDateAsc(ReminderStatus status, DateTime start, DateTime end);

    List<Event> findByStartDateGreaterThan(DateTime currentDate, Pageable pageable);

    List<Event> findByOrganizationAndStartDateGreaterThan(String organization, DateTime currentDate);

    List<Event> findByOrganizationAndEndDateLessThan(String organization, DateTime currentDate);

    List<Event> findByOrganizationAndState(String organization, EventState eventState);

    List<Event> findByCityAndStartDateGreaterThan(String city, DateTime currentDate, Pageable pageable);

    List<Event> findByCategoryAndStartDateGreaterThan(String category, DateTime currentDate, Pageable pageable);

    List<Event> findByCityAndCategoryAndStartDateGreaterThan(String city, String category, DateTime currentDate, Pageable pageable);
    
    List<Event> findAllBy(TextCriteria textCriteria, Pageable pageable);

}
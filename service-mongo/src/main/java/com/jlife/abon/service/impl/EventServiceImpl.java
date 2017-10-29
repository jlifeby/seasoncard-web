package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Event;
import com.jlife.abon.entity.Participant;
import com.jlife.abon.enumeration.EventState;
import com.jlife.abon.repository.EventRepository;
import com.jlife.abon.repository.ParticipantRepository;
import com.jlife.abon.service.EmailService;
import com.jlife.abon.service.EventService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 * @author Dzmitry Khralovich
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmailService emailService;

    @Override
    public void registerToEvent(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public List<Participant> getParticipants(String event) {
        return participantRepository.findByEvent(event);
    }

    @Override
    public List<Event> getActualEventSortedByDate() {
        Date currentDate = new Date();
        return eventRepository.findByStartDateGreaterThanOrderByStartDateAsc(currentDate);
    }

    @Override
    public List<Event> getActualEvents(int page, int size) {
        Pageable pageable = createStartDatePageable(page, size);
        return eventRepository.findByStartDateGreaterThan(DateTime.now(), pageable);
    }

    @Override
    public List<Event> getActualEventsByOrganization(String org) {
        return eventRepository.findByOrganizationAndStartDateGreaterThan(org, DateTime.now());
    }

    @Override
    public List<Event> getPastEvents(String org) {
        return eventRepository.findByOrganizationAndEndDateLessThan(org, DateTime.now());
    }

    @Override
    public List<Event> getDraftEvents(String org) {
        return eventRepository.findByOrganizationAndState(org, EventState.draft);
    }

    @Override
    public List<Event> getActualEventsByCity(String city, int page, int size) {
        Pageable pageable = createStartDatePageable(page, size);
        return eventRepository.findByCityAndStartDateGreaterThan(city, DateTime.now(), pageable);
    }

    @Override
    public List<Event> getActualEventsByCategory(String category, int page, int size) {
        Pageable pageable = createStartDatePageable(page, size);
        return eventRepository.findByCategoryAndStartDateGreaterThan(category, DateTime.now(), pageable);
    }

    @Override
    public List<Event> getActualEventsByCityAndCategory(String city, String category, int page, int size) {
        Pageable pageable = createStartDatePageable(page, size);
        return eventRepository.findByCityAndCategoryAndStartDateGreaterThan(city, category, DateTime.now(), pageable);
    }

    @Override
    public List<Event> searchFullText(String text, int page, int size) {
        Pageable pageable = createStartDatePageable(page, size);
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(text);
        return eventRepository.findAllBy(textCriteria, pageable);
    }

    @Override
    public List<Event> search(Query query) {
        return mongoTemplate.find(query, Event.class);
    }

    private Pageable createStartDatePageable(int page, int size) {
        return new PageRequest(page, size, Sort.Direction.ASC, "startDate");
    }

    public void removeEvent(String eventId) {
        Event event = eventRepository.findOne(eventId);
        eventRepository.delete(eventId);
        List<Participant> participants = participantRepository.findByEvent(eventId);
        for (Participant participant : participants) {
            if (event.isActual()) {
                emailService.sendRemovingInfo(event, participant);
            }
            participantRepository.delete(participant.getId());
        }
    }
}

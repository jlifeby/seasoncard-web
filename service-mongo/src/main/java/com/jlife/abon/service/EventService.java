package com.jlife.abon.service;

import com.jlife.abon.entity.Event;
import com.jlife.abon.entity.Participant;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 * @author Dzmitry Khralovich
 */
public interface EventService {

    void registerToEvent(Participant participant);

    List<Participant> getParticipants(String event);

    List<Event> getActualEventSortedByDate();

    List<Event> getActualEvents(int page, int size);

    List<Event> getActualEventsByOrganization(String org);

    List<Event> getPastEvents(String org);

    List<Event> getDraftEvents(String org);

    List<Event> getActualEventsByCity(String city, int page, int size);

    List<Event> getActualEventsByCategory(String category, int page, int size);

    List<Event> getActualEventsByCityAndCategory(String city, String category, int page, int size);
    
    List<Event> searchFullText(String text, int page, int size);
    
    List<Event> search(Query query);
    
    void removeEvent(String eventId);
}

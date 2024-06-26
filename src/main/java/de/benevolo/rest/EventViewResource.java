package de.benevolo.rest;

import de.benevolo.dto.EventViewDTO;
import de.benevolo.entity.EventView;
import de.benevolo.repo.EventViewRepo;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Path("/events/{eventId}/event-views")
public class EventViewResource {

    private final EventViewRepo eventViewRepo;

    @Inject
    public EventViewResource(EventViewRepo eventViewRepo) {
        this.eventViewRepo = eventViewRepo;
    }

    @GET
    @Path("/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public List<EventViewDTO> get(@PathParam("eventId") String eventId, @PathParam("dateFrom") String rawDateFrom, @PathParam("dateTo") String rawDateTo) {
        List<LocalDate> daysInRange = LocalDate.parse(rawDateFrom).datesUntil(LocalDate.parse(rawDateTo).plusDays(1)).toList();
        List<EventView> eventViews = eventViewRepo.findByEventId(eventId);
        List<EventViewDTO> result = new LinkedList<>();
        for (LocalDate date : daysInRange) {
            EventView searchResult = eventViews.stream().filter(item -> item.getOccurringDate().toString().equals(date.toString())).findFirst().orElse(null);
            if (searchResult != null) {
                result.add(new EventViewDTO(date, searchResult.getViews()));
            } else {
                result.add(new EventViewDTO(date, 0));
            }
        }
        return result;
    }

    @PATCH
    @Transactional
    public void addPageView(@PathParam("eventId") String eventId) {
        LocalDate viewDate = LocalDate.now();
        EventView eventView = eventViewRepo.findByEventIdAndOccurringDate(eventId, viewDate);
        eventView.increment();
        eventViewRepo.persist(eventView);
    }
}

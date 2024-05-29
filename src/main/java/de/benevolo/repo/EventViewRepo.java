package de.benevolo.repo;

import de.benevolo.entity.EventView;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EventViewRepo implements PanacheRepositoryBase<EventView, String> {

    public List<EventView> findByEventId(String eventId) {
        return list("eventId = :eventId", Parameters.with("eventId", eventId));
    }

    public EventView findByEventIdAndOccurringDate(String eventId, LocalDate occurringDate) {
        return list("eventId = :eventId and occurringDate = :occurringDate", Parameters.with("eventId", eventId).and("occurringDate", occurringDate)).stream().findFirst().orElse(new EventView(eventId, occurringDate));
    }

}

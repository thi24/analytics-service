package de.benevolo.repo;

import de.benevolo.entity.EventView;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EventViewRepo implements PanacheRepositoryBase<EventView, String> {

    public List<EventView> findByEventId(String eventId) {
        return list("eventId = :eventId", Parameters.with("eventId", eventId));
    }

}

package de.benevolo.repo;

import de.benevolo.entity.TicketValidation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TicketValidationRepo implements PanacheRepositoryBase<TicketValidation, String> {

    public TicketValidation findByEventIdAndDateAndTime(String eventId, LocalDate date, Time time) {
        return find("eventId = :eventId and validationDate = :date and validationTime = :time", Parameters.with("eventId", eventId).and("date", date).and("time", time)).firstResult();
    }

    public List<TicketValidation> findByEventIdAndDate(String eventId, LocalDate date) {
        return list("eventId = :eventId and validationDate = :date", Parameters.with("eventId", eventId).and("date", date));
    }

    public List<TicketValidation> findByEventId(String eventId) {
        return list("eventId = :eventId", Parameters.with("eventId", eventId));
    }
}

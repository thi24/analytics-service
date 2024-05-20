package de.benevolo.resource;

import de.benevolo.dto.TicketValidatedDTO;
import de.benevolo.entity.TicketValidationEntity;
import de.benevolo.repo.TicketValidationRepo;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Path("/validated-tickets")
public class TicketValidationResource {

    private final TicketValidationRepo ticketValidationRepo;

    @Inject
    public TicketValidationResource(TicketValidationRepo ticketValidationRepo) {
        this.ticketValidationRepo = ticketValidationRepo;
    }

    @GET
    @Path("/event/{eventId}/{date}")
    public List<TicketValidationEntity> getValidatedTicketsForDayAndEvent(@PathParam("eventId") String eventId, @PathParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<TicketValidationEntity> validatedTickets = ticketValidationRepo.findByEventIdAndDate(eventId, parsedDate);
        validatedTickets.sort(Comparator.comparing(TicketValidationEntity::getValidationTime));
        return validatedTickets;
    }

    @POST
    @Path("/event/{eventId}")
    @Transactional
    // Nochmal mit Tobi sprechen, eigentlich ist Zeit senden unnötig , TicketValidatedDTO ticketValidatedDTO
    public void saveValidationTimestamp(@PathParam("eventId") String eventId) {
        LocalDateTime now = LocalDateTime.now();
        // extract day from now:
        LocalDate day = now.toLocalDate();
        // Extract Hour from now:
        int hour = now.getHour();
        // create time object of the hour:
        Time time = Time.valueOf(LocalTime.of(hour, 0));

        TicketValidationEntity validatedTicket = ticketValidationRepo.findByEventIdAndDateAndTime(eventId, day, time);
        if(validatedTicket == null) {
            ticketValidationRepo.persist(new TicketValidationEntity(eventId, day, time, 1));
        } else {
            validatedTicket.setCount(validatedTicket.getCount() + 1);
        }
    }
}

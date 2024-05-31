package de.benevolo.rest;

import de.benevolo.entity.TicketValidation;
import de.benevolo.repo.TicketValidationRepo;
import io.quarkus.security.Authenticated;
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
    @Authenticated
    public List<TicketValidation> getValidatedTicketsForDayAndEvent(@PathParam("eventId") String eventId, @PathParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<TicketValidation> validatedTickets = ticketValidationRepo.findByEventIdAndDate(eventId, parsedDate);
        validatedTickets.sort(Comparator.comparing(TicketValidation::getValidationTime));
        return validatedTickets;
    }

    @POST
    @Path("/event/{eventId}")
    @Transactional
    public void saveValidationTimestamp(@PathParam("eventId") String eventId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate day = now.toLocalDate();
        int hour = now.getHour();
        Time time = Time.valueOf(LocalTime.of(hour, 0));

        TicketValidation validatedTicket = ticketValidationRepo.findByEventIdAndDateAndTime(eventId, day, time);
        if (validatedTicket == null) {
            ticketValidationRepo.persist(new TicketValidation(eventId, day, time, 1));
        } else {
            validatedTicket.setCount(validatedTicket.getCount() + 1);
        }
    }
}

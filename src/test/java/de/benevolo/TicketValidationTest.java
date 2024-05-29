package de.benevolo;

import de.benevolo.entity.TicketValidation;
import de.benevolo.repo.TicketValidationRepo;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class TicketValidationTest {
    @Inject
    TicketValidationRepo ticketValidationRepo;

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    void getValidatedTicketsForDayAndEventTest() {
        TicketValidation ticketValidation = ticketValidationRepo.findAll().firstResult();
        String eventId = ticketValidation.getEventId();
        ticketValidationRepo.findByEventId(eventId);
        long currentViews = ticketValidationRepo.
                findByEventIdAndDateAndTime(eventId, ticketValidation.getValidationDate(), ticketValidation.getValidationTime()).
                getCount();
        when().get("/validated-tickets/event/" + eventId + "/" + ticketValidation.getValidationDate())
                .then()
                .statusCode(200)
                .body(containsString("\"count\":" + currentViews));
    }

    @Test
    @Disabled
    void incrementValidationCountTest() {
        TicketValidation ticketValidation = ticketValidationRepo.findAll().firstResult();
        String eventId = ticketValidation.getEventId();
        ticketValidationRepo.findByEventId(eventId);
        long currentViews = ticketValidationRepo.
                findByEventIdAndDateAndTime(eventId, ticketValidation.getValidationDate(), ticketValidation.getValidationTime()).
                getCount();
        when().post("/validated-tickets/event/" + eventId)
                .then()
                .statusCode(204);
        long newViews = ticketValidationRepo.
                findByEventIdAndDateAndTime(eventId, ticketValidation.getValidationDate(), ticketValidation.getValidationTime()).
                getCount();
        assertThat(newViews, equalTo(currentViews + 1));
    }
}

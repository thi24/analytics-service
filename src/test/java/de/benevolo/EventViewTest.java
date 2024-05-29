package de.benevolo;

import de.benevolo.entity.EventView;
import de.benevolo.repo.EventViewRepo;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventViewTest {
    @Inject
    EventViewRepo eventViewRepo;


    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    @Transactional
    void getEventViewsForRangeTest() {
        EventView eventView = eventViewRepo.findAll().firstResult();
        if (eventView == null)
            // Create a new event view
            eventView = new EventView("123", LocalDate.now());
        eventViewRepo.persist(eventView);
        String eventId = eventView.getEventId();
        long views = eventView.getViews();
        LocalDate viewDate = eventView.getOccurringDate();

        when().get("/events/" + eventId + "/event-views/" + viewDate + "/" + viewDate)
                .then()
                .statusCode(200)
                .body(containsString("\"value\":" + views));
    }

    @Test
    @Transactional
    @Disabled
    void incrementPageViewTest() {
        String eventId = eventViewRepo.findAll().firstResult().getEventId();
        EventView eventView = eventViewRepo.findByEventIdAndOccurringDate(eventId, LocalDate.now());
        long currentViews = eventView.getViews();

        System.out.println("Current views: " + currentViews);

        when().patch("/events/" + eventId + "/event-views")
                .then()
                .statusCode(204);

        long newViews = eventViewRepo.findByEventIdAndOccurringDate(eventId, LocalDate.now()).getViews();
        System.out.println("New views: " + newViews);
        assertThat(newViews, equalTo(currentViews + 1));
    }
}

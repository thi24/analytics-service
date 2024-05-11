package de.benevolo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "event_view")
public class EventView extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "occurring_date")
    private LocalDate occurringDate;

    private int views;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDate getOccurringDate() {
        return occurringDate;
    }

    public void setOccurringDate(LocalDate occurringDate) {
        this.occurringDate = occurringDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}

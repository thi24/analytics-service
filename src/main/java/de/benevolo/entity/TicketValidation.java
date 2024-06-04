package de.benevolo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "ticket_validation")
public class TicketValidation {

    @Id
    @Column(name = "event_id")
    @JsonIgnore
    private String eventId;

    @Id
    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Id
    @Column(name = "validation_time")
    private Time validationTime;

    private int count;

    public TicketValidation() {
    }

    public TicketValidation(String eventId, LocalDate validationDate, Time validationTime, int count) {
        this.eventId = eventId;
        this.validationDate = validationDate;
        this.validationTime = validationTime;
        this.count = count;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public Time getValidationTime() {
        return validationTime;
    }

    public void setValidationTime(Time validationTime) {
        this.validationTime = validationTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

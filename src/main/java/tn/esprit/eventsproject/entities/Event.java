package tn.esprit.eventsproject.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Date date;
    private List<Participant> participants; // Utilisation de la classe Participant

    // Constructeurs
    public Event() {
    }

    public Event(Long id, String name, Date date, List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.participants = participants;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", participants=" + participants +
                '}';
    }
}

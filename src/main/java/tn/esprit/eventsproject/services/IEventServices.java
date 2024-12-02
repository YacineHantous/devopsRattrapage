package tn.esprit.eventsproject.services;

import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;

import java.time.LocalDate;
import java.util.List;

public interface IEventServices {

    Participant addParticipant(Participant participant);

    Event addAffectEvenParticipant(Event event, Long idParticipant);

    Event addAffectEvenParticipant(Event event);

    Logistics addAffectLog(Logistics logistics, Long eventId);

    List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin);

    void calculCout();

    Event createEvent(Event event);

    List<Logistics> getLogisticsByEventId(Long eventId);

    Event findEventById(Long id);

    List<Event> getAllEvents();

    Event updateEvent(Long eventId, Event event);

    void deleteEvent(Long eventId);
}

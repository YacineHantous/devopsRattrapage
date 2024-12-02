package tn.esprit.eventsproject.services;

import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServicesImpl implements IEventServices {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Event addAffectEvenParticipant(Event event, Long idParticipant) {
        Optional<Participant> participant = participantRepository.findById(idParticipant);
        if (participant.isEmpty()) {
            throw new IllegalArgumentException("Participant not found with id: " + idParticipant);
        }
        event.getParticipants().add(participant.get());
        return eventRepository.save(event);
    }

    @Override
    public Event addAffectEvenParticipant(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Logistics addAffectLog(Logistics logistics, Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new IllegalArgumentException("Event not found with id: " + eventId);
        }
        logistics.setEvent(event.get());
        return logisticsRepository.save(logistics);
    }

    @Override
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        return logisticsRepository.findByDateBetween(dateDebut, dateFin);
    }

    @Override
    public void calculCout() {
        // Implémenter le calcul du coût ici
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Logistics> getLogisticsByEventId(Long eventId) {
        return logisticsRepository.findByEventId(eventId);
    }

    @Override
    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event updateEvent(Long eventId, Event event) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isEmpty()) {
            throw new IllegalArgumentException("Event not found with id: " + eventId);
        }
        event.setId(eventId);
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new IllegalArgumentException("Event not found with id: " + eventId);
        }
        eventRepository.deleteById(eventId);
    }

    // Méthode pour récupérer les participants d'un événement
    @Override
    public List<Participant> getParticipants(Long eventId) {
        Event event = findEventById(eventId); // Récupère l'événement par son ID
        return event.getParticipants(); // Retourne la liste des participants associés à l'événement
    }
}

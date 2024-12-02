package tn.esprit.eventsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventServicesImpl implements EventServices {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    // Méthode pour récupérer un événement par son ID
    public Event findEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new ResourceNotFoundException("Event with id " + id + " not found");
        }
    }

    // Méthode pour ajouter un participant à un événement
    public Event addParticipantToEvent(Long eventId, Long participantId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        Optional<Participant> participantOpt = participantRepository.findById(participantId);

        if (eventOpt.isPresent() && participantOpt.isPresent()) {
            Event event = eventOpt.get();
            Participant participant = participantOpt.get();

            event.getParticipants().add(participant);
            participant.getEvents().add(event);

            eventRepository.save(event);
            participantRepository.save(participant);
            
            return event;
        } else {
            throw new ResourceNotFoundException("Event or Participant not found");
        }
    }

    // Méthode pour récupérer tous les événements
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Méthode pour créer un événement
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Méthode pour mettre à jour un événement
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event existingEvent = eventOpt.get();
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setNomEvent(updatedEvent.getNomEvent());
            existingEvent.setDateDebut(updatedEvent.getDateDebut());
            existingEvent.setDateFin(updatedEvent.getDateFin());
            existingEvent.setCout(updatedEvent.getCout());

            return eventRepository.save(existingEvent);
        } else {
            throw new ResourceNotFoundException("Event with id " + id + " not found");
        }
    }

    // Méthode pour supprimer un événement
    public void deleteEvent(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            eventRepository.delete(eventOpt.get());
        } else {
            throw new ResourceNotFoundException("Event with id " + id + " not found");
        }
    }
}

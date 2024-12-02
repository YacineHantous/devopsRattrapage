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
public class EventServicesImpl implements IEventServices { // Implémenter IEventServices

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
    public Event addAffectEvenParticipant(Event event, Long idParticipant) { // Modifié
        Optional<Event> eventOpt = eventRepository.findById(event.getIdEvent());
        Optional<Participant> participantOpt = participantRepository.findById(idParticipant);

        if (eventOpt.isPresent() && participantOpt.isPresent()) {
            Event existingEvent = eventOpt.get();
            Participant participant = participantOpt.get();

            existingEvent.getParticipants().add(participant);
            participant.getEvents().add(existingEvent);

            eventRepository.save(existingEvent);
            participantRepository.save(participant);
            
            return existingEvent;
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

    // Autres méthodes de IEventServices
    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Event addAffectEvenParticipant(Event event) {
        return addAffectEvenParticipant(event, event.getParticipants().iterator().next().getIdPart());
    }

    @Override
    public Logistics addAffectLog(Logistics logistics, String descriptionEvent) {
        // Implémentez ici la logique pour ajouter des logistiques à un événement
        return null;
    }

    @Override
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        // Implémentez la logique pour récupérer des logistiques entre deux dates
        return null;
    }

    @Override
    public void calculCout() {
        // Implémentez la logique pour calculer le coût des événements
    }

    @Override
    public List<Logistics> getLogisticsByEventId(Long eventId) {
        // Implémentez la logique pour récupérer les logistiques par événement
        return null;
    }
}

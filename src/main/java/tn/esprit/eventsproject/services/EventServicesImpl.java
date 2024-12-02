package tn.esprit.eventsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

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
    public Event addAffectEvenParticipant(Event event, Long idParticipant) {
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

    // Autres méthodes de gestion des événements
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

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

    public void deleteEvent(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            eventRepository.delete(eventOpt.get());
        } else {
            throw new ResourceNotFoundException("Event with id " + id + " not found");
        }
    }

    // Méthode pour ajouter une logistique à un événement
    @Override
    public Logistics addAffectLog(Logistics logistics, Long eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.getLogistics().add(logistics);
            logistics.setEvent(event);  // Assurez-vous que votre entité Logistics a une référence à Event
            eventRepository.save(event);
            return logisticsRepository.save(logistics);
        } else {
            throw new ResourceNotFoundException("Event with id " + eventId + " not found");
        }
    }

    // Autres méthodes liées à Logistics
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        return logisticsRepository.findByDateBetween(dateDebut, dateFin);
    }

    public List<Logistics> getLogisticsByEventId(Long eventId) {
        return logisticsRepository.findByEventId(eventId);
    }

    // Méthode pour calculer les coûts des événements
    @Override
    public void calculCout() {
        // Implémentez ici la logique pour calculer les coûts des événements
    }
}

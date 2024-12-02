package tn.esprit.eventsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.EventServicesImpl;  // Importer EventServicesImpl

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventServicesImpl eventServicesImpl;  // Utiliser EventServicesImpl pour l'injection de dépendance

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventServicesImpl.findEventById(eventId);  // Utiliser eventServicesImpl pour récupérer l'événement
    }

    @GetMapping("/{eventId}/participants")
    public List<Participant> getParticipants(@PathVariable Long eventId) {
        return eventServicesImpl.getParticipants(eventId);  // Utiliser eventServicesImpl pour récupérer les participants
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public Event addParticipantToEvent(@PathVariable Long eventId, @PathVariable Long participantId) {
        return eventServicesImpl.addAffectEvenParticipant(eventServicesImpl.findEventById(eventId), participantId);  // Affecter un participant à un événement
    }

    @PostMapping("/{eventId}/participants")
    public Event addParticipantsToEvent(@PathVariable Long eventId, @RequestBody List<Participant> participants) {
        Event event = eventServicesImpl.findEventById(eventId);  // Récupérer l'événement
        for (Participant participant : participants) {
            event.getParticipants().add(participant);
        }
        return eventServicesImpl.updateEvent(eventId, event);  // Mettre à jour l'événement avec les nouveaux participants
    }
}

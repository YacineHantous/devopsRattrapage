package tn.esprit.eventsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.EventServices;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventServices eventServices;

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventServices.findEventById(eventId); // Récupère l'événement par ID
    }

    @GetMapping("/{eventId}/participants")
    public List<Participant> getParticipants(@PathVariable Long eventId) {
        // Utilise le service pour obtenir les participants associés à l'événement
        return eventServices.getParticipants(eventId); // Retourne la liste des participants de l'événement
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public Event addParticipantToEvent(@PathVariable Long eventId, @PathVariable Long participantId) {
        // Ajoute un participant à un événement spécifique
        return eventServices.addAffectEvenParticipant(eventId, participantId); // Affecte un participant à l'événement
    }

    @PostMapping("/{eventId}/participants")
    public Event addParticipantsToEvent(@PathVariable Long eventId, @RequestBody List<Participant> participants) {
        Event event = eventServices.findEventById(eventId);
        for (Participant participant : participants) {
            event.getParticipants().add(participant);
        }
        return eventServices.updateEvent(eventId, event); // Met à jour l'événement avec les nouveaux participants
    }
}

package tn.esprit.eventsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.EventServicesImpl;  // Changer le nom de l'import ici

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventServicesImpl eventServicesImpl;  // Remplacez EventServices par EventServicesImpl

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventServicesImpl.findEventById(eventId);  // Utiliser eventServicesImpl
    }

    @GetMapping("/{eventId}/participants")
    public List<Participant> getParticipants(@PathVariable Long eventId) {
        return eventServicesImpl.getParticipants(eventId);  // Utiliser eventServicesImpl
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public Event addParticipantToEvent(@PathVariable Long eventId, @PathVariable Long participantId) {
        return eventServicesImpl.addAffectEvenParticipant(eventId, participantId);  // Utiliser eventServicesImpl
    }

    @PostMapping("/{eventId}/participants")
    public Event addParticipantsToEvent(@PathVariable Long eventId, @RequestBody List<Participant> participants) {
        Event event = eventServicesImpl.findEventById(eventId);  // Utiliser eventServiceImpl
        for (Participant participant : participants) {
            event.getParticipants().add(participant);
        }
        return eventServicesImpl.updateEvent(eventId, event);  // Utiliser eventServiceImpl
    }
}

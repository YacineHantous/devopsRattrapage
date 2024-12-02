package tn.esprit.eventsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.EventServiceImpl;  // Changer le nom de l'import ici

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventServiceImpl eventServiceImpl;  // Remplacez EventServices par EventServiceImpl

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventServiceImpl.findEventById(eventId);  // Utiliser eventServiceImpl
    }

    @GetMapping("/{eventId}/participants")
    public List<Participant> getParticipants(@PathVariable Long eventId) {
        return eventServiceImpl.getParticipants(eventId);  // Utiliser eventServiceImpl
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public Event addParticipantToEvent(@PathVariable Long eventId, @PathVariable Long participantId) {
        return eventServiceImpl.addAffectEvenParticipant(eventId, participantId);  // Utiliser eventServiceImpl
    }

    @PostMapping("/{eventId}/participants")
    public Event addParticipantsToEvent(@PathVariable Long eventId, @RequestBody List<Participant> participants) {
        Event event = eventServiceImpl.findEventById(eventId);  // Utiliser eventServiceImpl
        for (Participant participant : participants) {
            event.getParticipants().add(participant);
        }
        return eventServiceImpl.updateEvent(eventId, event);  // Utiliser eventServiceImpl
    }
}

package tn.esprit.eventsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.services.EventServices;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    @Autowired
    private EventServices eventServices;

    // Endpoint pour récupérer un événement par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventServices.findEventById(id);
        return ResponseEntity.ok(event);
    }

    // Endpoint pour récupérer tous les événements
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventServices.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Endpoint pour créer un événement
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventServices.createEvent(event);
        return ResponseEntity.status(201).body(createdEvent);
    }

    // Endpoint pour mettre à jour un événement
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventServices.updateEvent(id, updatedEvent);
        return ResponseEntity.ok(event);
    }

    // Endpoint pour supprimer un événement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventServices.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour ajouter un participant à un événement
    @PostMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Event> addParticipantToEvent(
            @PathVariable Long eventId,
            @PathVariable Long participantId
    ) {
        Event updatedEvent = eventServices.addParticipantToEvent(eventId, participantId);
        return ResponseEntity.ok(updatedEvent);
    }
}

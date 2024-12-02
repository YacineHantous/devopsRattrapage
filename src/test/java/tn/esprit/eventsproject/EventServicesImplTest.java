package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.services.EventServicesImpl;
import tn.esprit.eventsproject.repositories.EventRepository;


public class EventServicesImplTest {

    private EventRepository eventRepository;
    private EventServicesImpl eventService;

    @BeforeEach
    void setUp() {
        eventRepository = Mockito.mock(EventRepository.class); // Mock du repository
        eventService = new EventServicesImpl(eventRepository); // Service à tester
    }

    @Test
    void testCreateEvent() {
        Event event = new Event("Event Test", LocalDate.now());

        when(eventRepository.save(any(Event.class))).thenReturn(event); // Simuler le comportement du repo
        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals("Event Test", createdEvent.getName());
    }

    @Test
    void testFindEventById() {
        Event event = new Event("Event Test", LocalDate.now());
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> foundEvent = eventService.findEventById(1L);

        assertTrue(foundEvent.isPresent());
        assertEquals("Event Test", foundEvent.get().getName());
    }
}

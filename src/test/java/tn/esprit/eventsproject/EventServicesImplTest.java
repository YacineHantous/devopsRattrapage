package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServicesImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks et injecte les dépendances
    }

    @Test
    void testCreateEvent() {
        Event event = new Event("Event Test", LocalDate.now(), LocalDate.now().plusDays(1), 500);
        when(eventRepository.save(any(Event.class))).thenReturn(event); // Simuler le comportement du repo

        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals("Event Test", createdEvent.getNomEvent());
    }

    @Test
    void testFindEventById() {
        Event event = new Event("Event Test", LocalDate.now(), LocalDate.now().plusDays(1), 500);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event)); // Simuler une recherche réussie

        Optional<Event> foundEvent = eventService.findEventById(1L);

        assertTrue(foundEvent.isPresent());
        assertEquals("Event Test", foundEvent.get().getNomEvent());
    }

    @Test
    void testCalculateTotalCost() {
        Event event = new Event("Event Test", LocalDate.now(), LocalDate.now().plusDays(1), 500);
        
        // Simuler la logistique
        Logistics logistics1 = new Logistics("Logistic 1", false, 50, 10); // Coût 500
        Logistics logistics2 = new Logistics("Logistic 2", true, 30, 5);  // Coût 150
        Set<Logistics> logisticsSet = new HashSet<>();
        logisticsSet.add(logistics1);
        logisticsSet.add(logistics2);

        event.setLogistics(logisticsSet);
        
        float totalCost = event.calculateTotalCost(); // Devrait être 500 + 150 + 500 = 1150
        
        assertEquals(1150.0f, totalCost, "Le coût total devrait être calculé correctement.");
    }
}

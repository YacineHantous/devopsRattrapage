package com.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.services.IEventServices;

public class IEventServicesTest {

    @Test
    void testInterfaceMethods() {
        // Vérifier les signatures et les comportements si nécessaire
        assertTrue(IEventServices.class.isInterface());
    }
}

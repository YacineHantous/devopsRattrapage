package tn.esprit.eventsproject.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import tn.esprit.eventsproject.entities.Logistics;

public class LogisticsTest {

    @Test
    void testGetCost() {
        // Créer un objet Logistics
        Logistics logistics = new Logistics();
        logistics.setPrixUnit(50.0f);  // Prix unitaire
        logistics.setQuantite(10);     // Quantité

        // Calculer le coût attendu
        float expectedCost = 50.0f * 10;  // Coût attendu = prix unitaire * quantité
        
        // Vérifier que la méthode getCost retourne le bon résultat
        assertEquals(expectedCost, logistics.getCost(), "Le coût total de la logistique doit être correct");
    }
}

package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Logistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idLog;

    String description;
    boolean reserve;  // Indique si la logistique est réservée
    float prixUnit;   // Prix unitaire de la logistique
    int quantite;     // Quantité de logistique

    // Méthode pour calculer le coût total de la logistique
    public float getCost() {
        return prixUnit * quantite; // Le coût total est le prix unitaire multiplié par la quantité
    }

    // Méthode pour vérifier si la logistique est réservée
    public boolean isReserve() {
        return reserve;
    }
}

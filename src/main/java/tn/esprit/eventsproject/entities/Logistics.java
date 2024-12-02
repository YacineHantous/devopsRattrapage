package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Logistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLog;

    private String description;
    private boolean reserve;  // Indique si la logistique est réservée
    private float prixUnit;   // Prix unitaire de la logistique
    private int quantite;     // Quantité de logistique

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // Référence à l'événement auquel la logistique est associée

    public float getCost() {
        return prixUnit * quantite; // Le coût total est le prix unitaire multiplié par la quantité
    }

    public boolean isReserve() {
        return reserve;
    }
}

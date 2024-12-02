package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idEvent;

    @NotBlank(message = "La description ne doit pas être vide")
    String description;

    @NotBlank(message = "Le nom de l'événement ne doit pas être vide")
    String nomEvent; // Nouveau champ ajouté

    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    LocalDate dateDebut;

    @FutureOrPresent(message = "La date de fin doit être aujourd'hui ou dans le futur")
    LocalDate dateFin;

    @Positive(message = "Le coût doit être positif")
    float cout;

    @ManyToMany(mappedBy = "events")
    Set<Participant> participants;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Logistics> logistics;

    // Méthode personnalisée pour calculer le coût total
    public float calculateTotalCost() {
        return logistics.stream()
                        .map(Logistics::getCost)
                        .reduce(cout, Float::sum); // Somme des coûts de la logistique + coût de l'événement
    }

    // Méthode pour vérifier si l'événement est valide
    public boolean isValid() {
        return dateDebut.isBefore(dateFin);
    }
}

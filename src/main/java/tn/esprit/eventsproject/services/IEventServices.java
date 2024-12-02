package tn.esprit.eventsproject.services;

import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;

import java.time.LocalDate;
import java.util.List;

public interface IEventServices {

    // Méthode pour ajouter un participant
    public Participant addParticipant(Participant participant);

    // Méthode pour ajouter un participant à un événement
    public Event addAffectEvenParticipant(Event event, Long idParticipant); // Changez 'int' en 'Long'

    // Méthode pour ajouter un participant à un événement, peut être utilisée pour plusieurs participants
    public Event addAffectEvenParticipant(Event event);

    // Méthode pour affecter des logistiques à un événement
    public Logistics addAffectLog(Logistics logistics, String descriptionEvent);

    // Méthode pour obtenir les logistiques entre deux dates
    public List<Logistics> getLogisticsDates(LocalDate date_debut, LocalDate date_fin);

    // Méthode pour calculer les coûts des événements
    public void calculCout();

    // Méthode pour créer un événement
    public Event createEvent(Event event);

    // Méthode pour récupérer les logistiques par ID d'événement
    public List<Logistics> getLogisticsByEventId(Long eventId);
}

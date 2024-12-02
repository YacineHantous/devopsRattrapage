package tn.esprit.eventsproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServicesImpl implements IEventServices {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final LogisticsRepository logisticsRepository;

    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Event addAffectEvenParticipant(Event event, Long idParticipant) { // Utilisation de Long
        Participant participant = participantRepository.findById(idParticipant).orElse(null);
        if (participant != null) {
            if (participant.getEvents() == null) {
                Set<Event> events = new HashSet<>();
                events.add(event);
                participant.setEvents(events);
            } else {
                participant.getEvents().add(event);
            }
        }
        return eventRepository.save(event);
    }

    @Override
    public Event addAffectEvenParticipant(Event event) { // Méthode pour ajouter plusieurs participants
        Set<Participant> participants = event.getParticipants();
        for (Participant aParticipant : participants) {
            Participant participant = participantRepository.findById(aParticipant.getIdPart()).orElse(null);
            if (participant != null) {
                if (participant.getEvents() == null) {
                    Set<Event> events = new HashSet<>();
                    events.add(event);
                    participant.setEvents(events);
                } else {
                    participant.getEvents().add(event);
                }
            }
        }
        return eventRepository.save(event);
    }

    @Override
    public Logistics addAffectLog(Logistics logistics, String descriptionEvent) {
        Event event = eventRepository.findByDescription(descriptionEvent);
        if (event != null) {
            if (event.getLogistics() == null) {
                Set<Logistics> logisticsSet = new HashSet<>();
                logisticsSet.add(logistics);
                event.setLogistics(logisticsSet);
            } else {
                event.getLogistics().add(logistics);
            }
            eventRepository.save(event);
        }
        return logisticsRepository.save(logistics);
    }

    @Override
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        List<Event> events = eventRepository.findByDateDebutBetween(dateDebut, dateFin);

        List<Logistics> logisticsList = new ArrayList<>();
        for (Event event : events) {
            if (!event.getLogistics().isEmpty()) {
                Set<Logistics> logisticsSet = event.getLogistics();
                for (Logistics logistics : logisticsSet) {
                    if (logistics.isReserve()) {
                        logisticsList.add(logistics);
                    }
                }
            }
        }
        return logisticsList.isEmpty() ? null : logisticsList;
    }

    @Scheduled(cron = "*/60 * * * * *")
    @Override
    public void calculCout() {
        List<Event> events = eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache("Tounsi", "Ahmed", Tache.ORGANISATEUR);
        float somme;
        for (Event event : events) {
            somme = 0f;
            log.info("Calcul du coût pour l'événement: " + event.getDescription());
            Set<Logistics> logisticsSet = event.getLogistics();
            for (Logistics logistics : logisticsSet) {
                if (logistics.isReserve()) {
                    somme += logistics.getPrixUnit() * logistics.getQuantite();
                }
            }
            event.setCout(somme);
            eventRepository.save(event);
            log.info("Coût de l'événement " + event.getDescription() + " est " + somme);
        }
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Logistics> getLogisticsByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        return event != null ? new ArrayList<>(event.getLogistics()) : null;
    }
}

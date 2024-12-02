package tn.esprit.eventsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.eventsproject.entities.Logistics;

import java.util.List;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {

    List<Logistics> findByEventId(Long eventId);

    List<Logistics> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);
}

package tn.esprit.eventsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.eventsproject.entities.Logistics;

import java.time.LocalDate;
import java.util.List;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {

    List<Logistics> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);

    List<Logistics> findByEventId(Long eventId);
}

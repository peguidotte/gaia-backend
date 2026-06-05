package br.com.fiap.gaia.repository;

import br.com.fiap.gaia.domain.model.Observation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findByFieldIdOrderByObservedAtDesc(Long fieldId);
}

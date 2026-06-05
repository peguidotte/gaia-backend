package br.com.fiap.gaia.repository;

import br.com.fiap.gaia.domain.model.Recommendation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByFieldIdOrderByCreatedAtDesc(Long fieldId);
}

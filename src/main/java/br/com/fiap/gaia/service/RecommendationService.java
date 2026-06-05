package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Observation;
import br.com.fiap.gaia.domain.model.Recommendation;
import br.com.fiap.gaia.repository.RecommendationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final FieldService fieldService;
    private final ObservationService observationService;
    private final RecommendationEngine recommendationEngine;

    public RecommendationService(
            RecommendationRepository recommendationRepository,
            FieldService fieldService,
            ObservationService observationService,
            RecommendationEngine recommendationEngine
    ) {
        this.recommendationRepository = recommendationRepository;
        this.fieldService = fieldService;
        this.observationService = observationService;
        this.recommendationEngine = recommendationEngine;
    }

    @Transactional
    public Recommendation generate(Long fieldId) {
        Field field = fieldService.findById(fieldId);
        List<Observation> observations = observationService.findByField(fieldId);
        if (observations.isEmpty()) {
            observations = observationService.generateDemoPair(fieldId);
        }
        return recommendationRepository.save(recommendationEngine.generate(field, observations));
    }

    @Transactional(readOnly = true)
    public List<Recommendation> findByField(Long fieldId) {
        fieldService.findById(fieldId);
        return recommendationRepository.findByFieldIdOrderByCreatedAtDesc(fieldId);
    }
}

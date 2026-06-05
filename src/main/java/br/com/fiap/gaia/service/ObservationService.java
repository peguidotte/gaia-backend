package br.com.fiap.gaia.service;

import br.com.fiap.gaia.api.dto.ObservationRequest;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Observation;
import br.com.fiap.gaia.repository.ObservationRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservationService {

    private final ObservationRepository observationRepository;
    private final FieldService fieldService;
    private final ClimateDataProvider climateDataProvider;

    public ObservationService(ObservationRepository observationRepository, FieldService fieldService, ClimateDataProvider climateDataProvider) {
        this.observationRepository = observationRepository;
        this.fieldService = fieldService;
        this.climateDataProvider = climateDataProvider;
    }

    @Transactional
    public Observation create(ObservationRequest request) {
        Field field = fieldService.findById(request.fieldId());
        OffsetDateTime observedAt = request.observedAt() == null ? OffsetDateTime.now() : request.observedAt();
        Observation observation = switch (request.type()) {
            case "SATELLITE" -> climateDataProvider.satelliteObservationFor(field, observedAt);
            case "CLIMATE" -> climateDataProvider.climateObservationFor(field, observedAt);
            default -> throw new IllegalArgumentException("Tipo de observacao invalido.");
        };
        return observationRepository.save(observation);
    }

    @Transactional(readOnly = true)
    public List<Observation> findAll() {
        return observationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Observation> findByField(Long fieldId) {
        fieldService.findById(fieldId);
        return observationRepository.findByFieldIdOrderByObservedAtDesc(fieldId);
    }

    @Transactional
    public List<Observation> generateDemoPair(Long fieldId) {
        Field field = fieldService.findById(fieldId);
        OffsetDateTime now = OffsetDateTime.now();
        return observationRepository.saveAll(List.of(
                climateDataProvider.satelliteObservationFor(field, now.minusHours(2)),
                climateDataProvider.climateObservationFor(field, now.minusHours(1))
        ));
    }
}

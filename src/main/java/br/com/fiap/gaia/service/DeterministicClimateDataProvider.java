package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.ClimateObservation;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.SatelliteObservation;
import br.com.fiap.gaia.domain.vo.VegetationIndex;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

@Service
public class DeterministicClimateDataProvider implements ClimateDataProvider {

    private final ObservationScoringService scoringService;

    public DeterministicClimateDataProvider(ObservationScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @Override
    public SatelliteObservation satelliteObservationFor(Field field, OffsetDateTime observedAt) {
        int seed = Math.abs((field.getId() + observedAt.toLocalDate().toEpochDay()) % 100L) == 0
                ? 1
                : (int) Math.abs((field.getId() + observedAt.toLocalDate().toEpochDay()) % 100L);
        double ndvi = round(0.35 + (seed % 55) / 100.0);
        double cloudCoverage = round(10 + (seed * 7 % 80));
        VegetationIndex vegetationIndex = new VegetationIndex(ndvi);
        return new SatelliteObservation(field, observedAt, vegetationIndex, cloudCoverage, scoringService.satelliteRisk(vegetationIndex, cloudCoverage));
    }

    @Override
    public ClimateObservation climateObservationFor(Field field, OffsetDateTime observedAt) {
        int seed = (int) Math.abs((field.getId() * 13 + observedAt.getDayOfYear()) % 100);
        double rainfall = round(seed % 60);
        double temperature = round(20 + (seed % 16));
        double humidity = round(30 + (seed * 3 % 65));
        return new ClimateObservation(field, observedAt, rainfall, temperature, humidity, scoringService.climateRisk(rainfall, temperature, humidity));
    }

    private static double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}

package br.com.fiap.gaia.api.dto;

import br.com.fiap.gaia.domain.model.ClimateObservation;
import br.com.fiap.gaia.domain.model.Observation;
import br.com.fiap.gaia.domain.model.SatelliteObservation;
import java.time.OffsetDateTime;

public record ObservationResponse(
        Long id,
        Long fieldId,
        String type,
        OffsetDateTime observedAt,
        String summary,
        Integer riskScore,
        String riskLevel,
        String producerMessage,
        Double ndvi,
        Double cloudCoverage,
        Double rainfallMm,
        Double temperatureCelsius,
        Double humidityPercent
) {
    public static ObservationResponse from(Observation observation) {
        if (observation instanceof SatelliteObservation satellite) {
            return new ObservationResponse(
                    satellite.getId(),
                    satellite.getField().getId(),
                    "SATELLITE",
                    satellite.getObservedAt(),
                    satellite.getSummary(),
                    satellite.getRiskScore().value(),
                    satellite.getRiskScore().level(),
                    satellite.producerMessage(),
                    satellite.getVegetationIndex().ndvi(),
                    satellite.getCloudCoverage(),
                    null,
                    null,
                    null
            );
        }
        ClimateObservation climate = (ClimateObservation) observation;
        return new ObservationResponse(
                climate.getId(),
                climate.getField().getId(),
                "CLIMATE",
                climate.getObservedAt(),
                climate.getSummary(),
                climate.getRiskScore().value(),
                climate.getRiskScore().level(),
                climate.producerMessage(),
                null,
                null,
                climate.getRainfallMm(),
                climate.getTemperatureCelsius(),
                climate.getHumidityPercent()
        );
    }
}

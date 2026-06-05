package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.vo.RiskScore;
import br.com.fiap.gaia.domain.vo.VegetationIndex;

public interface ObservationScoringService {
    RiskScore satelliteRisk(VegetationIndex vegetationIndex, double cloudCoverage);

    RiskScore climateRisk(double rainfallMm, double temperatureCelsius, double humidityPercent);
}

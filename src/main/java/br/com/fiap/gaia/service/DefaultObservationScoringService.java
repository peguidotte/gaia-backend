package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.vo.RiskScore;
import br.com.fiap.gaia.domain.vo.VegetationIndex;
import org.springframework.stereotype.Service;

@Service
public class DefaultObservationScoringService implements ObservationScoringService {

    @Override
    public RiskScore satelliteRisk(VegetationIndex vegetationIndex, double cloudCoverage) {
        int vegetationPenalty = (int) Math.round((1 - vegetationIndex.ndvi()) * 80);
        int cloudPenalty = cloudCoverage > 60 ? 10 : 0;
        return new RiskScore(Math.min(100, vegetationPenalty + cloudPenalty));
    }

    @Override
    public RiskScore climateRisk(double rainfallMm, double temperatureCelsius, double humidityPercent) {
        int dryPenalty = rainfallMm < 5 ? 35 : 0;
        int heatPenalty = temperatureCelsius > 31 ? 30 : 0;
        int humidityPenalty = humidityPercent < 35 ? 20 : 0;
        int rainPenalty = rainfallMm > 45 ? 25 : 0;
        return new RiskScore(Math.min(100, dryPenalty + heatPenalty + humidityPenalty + rainPenalty));
    }
}

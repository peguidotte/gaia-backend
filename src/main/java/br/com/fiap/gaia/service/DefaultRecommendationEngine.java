package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.ClimateObservation;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Observation;
import br.com.fiap.gaia.domain.model.Recommendation;
import br.com.fiap.gaia.domain.model.SatelliteObservation;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultRecommendationEngine implements RecommendationEngine {

    @Override
    public Recommendation generate(Field field, List<Observation> observations) {
        Observation riskiest = observations.stream()
                .max(Comparator.comparingInt(observation -> observation.getRiskScore().value()))
                .orElseThrow(() -> new IllegalStateException("Nao ha observacoes suficientes para recomendar."));

        if (riskiest instanceof SatelliteObservation satellite && satellite.getVegetationIndex().ndvi() < 0.45) {
            return new Recommendation(
                    field,
                    "ALTA",
                    "Inspecionar vegetacao do talhao",
                    "O indice de vegetacao indica area sob atencao.",
                    "Priorize uma vistoria em campo e verifique irrigacao, pragas ou falhas de plantio."
            );
        }
        if (riskiest instanceof ClimateObservation climate && climate.getRainfallMm() < 5 && climate.getTemperatureCelsius() > 31) {
            return new Recommendation(
                    field,
                    "ALTA",
                    "Avaliar necessidade de irrigacao",
                    "O clima recente combina baixa chuva e temperatura elevada.",
                    "Confira umidade do solo e antecipe irrigacao se a cultura estiver em fase sensivel."
            );
        }
        if (riskiest.getRiskScore().value() >= 40) {
            return new Recommendation(
                    field,
                    "MEDIA",
                    "Acompanhar talhao nas proximas 48 horas",
                    "O risco ambiental esta em nivel medio.",
                    "Mantenha monitoramento e compare novas observacoes antes de intervir."
            );
        }
        return new Recommendation(
                field,
                "BAIXA",
                "Manter rotina de monitoramento",
                "Vegetacao e clima estao dentro de uma faixa estavel.",
                "Nenhuma intervencao imediata e recomendada."
        );
    }
}

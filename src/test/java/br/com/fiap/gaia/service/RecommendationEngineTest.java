package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.ClimateObservation;
import br.com.fiap.gaia.domain.model.Farm;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Recommendation;
import br.com.fiap.gaia.domain.model.SatelliteObservation;
import br.com.fiap.gaia.domain.vo.FieldArea;
import br.com.fiap.gaia.domain.vo.GeoPoint;
import br.com.fiap.gaia.domain.vo.RiskScore;
import br.com.fiap.gaia.domain.vo.VegetationIndex;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecommendationEngineTest {

    private final DefaultRecommendationEngine engine = new DefaultRecommendationEngine();

    @Test
    void prioritizesLowVegetationInspection() {
        Field field = field();
        SatelliteObservation satellite = new SatelliteObservation(
                field,
                OffsetDateTime.now(),
                new VegetationIndex(0.35),
                12.0,
                new RiskScore(75)
        );

        Recommendation recommendation = engine.generate(field, List.of(satellite));

        assertThat(recommendation.getPriority()).isEqualTo("ALTA");
        assertThat(recommendation.getTitle()).contains("vegetacao");
    }

    @Test
    void recommendsIrrigationForDryHeat() {
        Field field = field();
        ClimateObservation climate = new ClimateObservation(
                field,
                OffsetDateTime.now(),
                1.0,
                34.0,
                32.0,
                new RiskScore(85)
        );

        Recommendation recommendation = engine.generate(field, List.of(climate));

        assertThat(recommendation.getPriority()).isEqualTo("ALTA");
        assertThat(recommendation.getTitle()).contains("irrigacao");
    }

    private Field field() {
        return new Field(
                "Talhao Teste",
                "Soja",
                new FieldArea(10.0),
                new GeoPoint(-21.0, -47.0),
                new Farm("Fazenda Teste", "Produtor", "Cidade", "SP")
        );
    }
}

package br.com.fiap.gaia.domain.model;

import br.com.fiap.gaia.domain.vo.RiskScore;
import br.com.fiap.gaia.domain.vo.VegetationIndex;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.time.OffsetDateTime;

@Entity
@DiscriminatorValue("SATELLITE")
public class SatelliteObservation extends Observation {

    @Embedded
    private VegetationIndex vegetationIndex;
    private Double cloudCoverage;

    protected SatelliteObservation() {
    }

    public SatelliteObservation(Field field, OffsetDateTime observedAt, VegetationIndex vegetationIndex, Double cloudCoverage, RiskScore riskScore) {
        super(field, observedAt, vegetationIndex.status(), riskScore);
        this.vegetationIndex = vegetationIndex;
        this.cloudCoverage = cloudCoverage;
    }

    @Override
    public String producerMessage() {
        return "%s. Cobertura de nuvens: %.0f%%.".formatted(vegetationIndex.status(), cloudCoverage);
    }

    public VegetationIndex getVegetationIndex() {
        return vegetationIndex;
    }

    public Double getCloudCoverage() {
        return cloudCoverage;
    }
}

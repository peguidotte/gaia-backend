package br.com.fiap.gaia.domain.model;

import br.com.fiap.gaia.domain.vo.RiskScore;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "observations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "observation_type")
public abstract class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Field field;

    private OffsetDateTime observedAt;
    private String summary;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "risk_score"))
    private RiskScore riskScore;

    protected Observation() {
    }

    protected Observation(Field field, OffsetDateTime observedAt, String summary, RiskScore riskScore) {
        this.field = field;
        this.observedAt = observedAt;
        this.summary = summary;
        this.riskScore = riskScore;
    }

    public abstract String producerMessage();

    public Long getId() {
        return id;
    }

    public Field getField() {
        return field;
    }

    public OffsetDateTime getObservedAt() {
        return observedAt;
    }

    public String getSummary() {
        return summary;
    }

    public RiskScore getRiskScore() {
        return riskScore;
    }
}

package br.com.fiap.gaia.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record RiskScore(Integer value) {

    public RiskScore {
        if (value == null || value < 0 || value > 100) {
            throw new IllegalArgumentException("Risco deve estar entre 0 e 100.");
        }
    }

    public String level() {
        if (value >= 70) {
            return "ALTO";
        }
        if (value >= 40) {
            return "MEDIO";
        }
        return "BAIXO";
    }
}

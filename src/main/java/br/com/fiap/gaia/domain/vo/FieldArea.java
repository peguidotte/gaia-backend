package br.com.fiap.gaia.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record FieldArea(Double hectares) {

    public FieldArea {
        if (hectares == null || hectares <= 0) {
            throw new IllegalArgumentException("Area do talhao deve ser maior que zero.");
        }
    }
}

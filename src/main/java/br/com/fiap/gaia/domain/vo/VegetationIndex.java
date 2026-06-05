package br.com.fiap.gaia.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record VegetationIndex(Double ndvi) {

    public VegetationIndex {
        if (ndvi == null || ndvi < 0 || ndvi > 1) {
            throw new IllegalArgumentException("NDVI deve estar entre 0 e 1.");
        }
    }

    public String status() {
        if (ndvi >= 0.70) {
            return "Vegetacao saudavel";
        }
        if (ndvi >= 0.45) {
            return "Vegetacao estavel";
        }
        return "Vegetacao sob atencao";
    }
}

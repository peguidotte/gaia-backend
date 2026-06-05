package br.com.fiap.gaia.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record GeoPoint(Double latitude, Double longitude) {

    public GeoPoint {
        if (latitude == null || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude deve estar entre -90 e 90.");
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude deve estar entre -180 e 180.");
        }
    }
}

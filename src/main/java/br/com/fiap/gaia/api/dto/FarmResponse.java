package br.com.fiap.gaia.api.dto;

import br.com.fiap.gaia.domain.model.Farm;
import java.time.OffsetDateTime;

public record FarmResponse(
        Long id,
        String name,
        String ownerName,
        String city,
        String state,
        OffsetDateTime createdAt
) {
    public static FarmResponse from(Farm farm) {
        return new FarmResponse(farm.getId(), farm.getName(), farm.getOwnerName(), farm.getCity(), farm.getState(), farm.getCreatedAt());
    }
}

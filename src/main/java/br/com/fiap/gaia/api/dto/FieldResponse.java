package br.com.fiap.gaia.api.dto;

import br.com.fiap.gaia.domain.model.Field;
import java.time.OffsetDateTime;

public record FieldResponse(
        Long id,
        String name,
        String crop,
        Double hectares,
        Double latitude,
        Double longitude,
        Long farmId,
        OffsetDateTime createdAt
) {
    public static FieldResponse from(Field field) {
        return new FieldResponse(
                field.getId(),
                field.getName(),
                field.getCrop(),
                field.getArea().hectares(),
                field.getCentroid().latitude(),
                field.getCentroid().longitude(),
                field.getFarm().getId(),
                field.getCreatedAt()
        );
    }
}

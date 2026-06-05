package br.com.fiap.gaia.api.dto;

import br.com.fiap.gaia.domain.model.Recommendation;
import java.time.OffsetDateTime;

public record RecommendationResponse(
        Long id,
        Long fieldId,
        String priority,
        String title,
        String reason,
        String action,
        OffsetDateTime createdAt
) {
    public static RecommendationResponse from(Recommendation recommendation) {
        return new RecommendationResponse(
                recommendation.getId(),
                recommendation.getField().getId(),
                recommendation.getPriority(),
                recommendation.getTitle(),
                recommendation.getReason(),
                recommendation.getAction(),
                recommendation.getCreatedAt()
        );
    }
}

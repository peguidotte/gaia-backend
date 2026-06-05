package br.com.fiap.gaia.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.OffsetDateTime;

public record ObservationRequest(
        @NotNull Long fieldId,
        @NotNull @Pattern(regexp = "SATELLITE|CLIMATE") String type,
        OffsetDateTime observedAt
) {
}

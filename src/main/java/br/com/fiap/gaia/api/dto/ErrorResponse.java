package br.com.fiap.gaia.api.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fields
) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(OffsetDateTime.now(), status, error, message, Map.of());
    }
}

package br.com.fiap.gaia.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FarmRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 120) String ownerName,
        @NotBlank @Size(max = 80) String city,
        @NotBlank @Size(min = 2, max = 2) String state
) {
}

package org.example.infrastructure.currencyreceiver.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CurrencyCommandRequestDto(
        @NotNull()
        @NotEmpty()
        String name,
        @NotNull()
        @NotEmpty()
        String currency
) {
}

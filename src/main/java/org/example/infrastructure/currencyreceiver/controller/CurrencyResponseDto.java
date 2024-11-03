package org.example.infrastructure.currencyreceiver.controller;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CurrencyResponseDto(
        BigDecimal value
) {
}

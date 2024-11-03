package org.example.infrastructure.currencyreceiver.controller;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record RequestDetailsResponseDto(
        String currency,
        String name,
        LocalDateTime date,
        BigDecimal value
) {
}

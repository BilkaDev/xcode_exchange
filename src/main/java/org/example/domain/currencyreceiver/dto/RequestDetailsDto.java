package org.example.domain.currencyreceiver.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record RequestDetailsDto(
        String currency,
        String name,
        LocalDateTime date,
        BigDecimal value
) {
}

package org.example.domain.currencyreceiver.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CurrencyDto(BigDecimal value) {
}

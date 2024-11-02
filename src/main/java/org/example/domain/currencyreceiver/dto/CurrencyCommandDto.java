package org.example.domain.currencyreceiver.dto;

import lombok.Builder;

@Builder
public record CurrencyCommandDto(
        String name,
        String currency
) {
}

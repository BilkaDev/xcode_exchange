package org.example.domain.currencyreceiver;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class Currency {
    private String currency;
    private String name;
    private LocalDate date;
    private BigDecimal value;
}

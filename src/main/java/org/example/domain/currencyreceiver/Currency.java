package org.example.domain.currencyreceiver;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class Currency {
    private String currency;
    private String name;
    private LocalDateTime date;
    private BigDecimal value;
}

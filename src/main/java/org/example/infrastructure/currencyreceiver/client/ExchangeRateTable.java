package org.example.infrastructure.currencyreceiver.client;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeRateTable {
    private String table;
    private String no;
    private String effectiveDate;
    private List<ExchangeRate> rates;
}

package org.example.infrastructure.currencyreceiver.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRate {
    private String currency;
    private String code;
    private double mid;
}

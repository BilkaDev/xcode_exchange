package org.example.domain.currencyreceiver;

import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;

import java.math.BigDecimal;

public interface RemoteCurrencyReceiver {
    BigDecimal getCurrency(CurrencyCommandDto currencyCommand);
}

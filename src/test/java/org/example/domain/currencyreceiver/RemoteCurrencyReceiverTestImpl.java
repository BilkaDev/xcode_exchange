package org.example.domain.currencyreceiver;

import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;

import java.math.BigDecimal;

public class RemoteCurrencyReceiverTestImpl implements RemoteCurrencyReceiver {
    @Override
    public BigDecimal getCurrency(CurrencyCommandDto currencyCommand) {
        return new BigDecimal("0.12345");
    }
}

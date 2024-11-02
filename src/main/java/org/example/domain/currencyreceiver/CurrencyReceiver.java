package org.example.domain.currencyreceiver;

import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;
import org.example.domain.currencyreceiver.dto.RequestDetailsDto;

import java.util.List;

public interface CurrencyReceiver {
    CurrencyDto getCurrentCurrency(CurrencyCommandDto currencyCommand);

    List<RequestDetailsDto> getRequests();
}

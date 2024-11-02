package org.example.domain.currencyreceiver;

import lombok.AllArgsConstructor;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
public class CurrencyReceiverFacade {
    private final CurrencyRepository currencyRepository;
    private final RemoteCurrencyReceiver remoteCurrencyReceiver;


    public CurrencyDto getCurrentCurrency(CurrencyCommandDto currencyCommand) {
        BigDecimal value = remoteCurrencyReceiver.getCurrency(currencyCommand);
        CurrencyDto currencyDto = CurrencyDto.builder()
                .value(value)
                .build();

        Currency currencyEntity = Currency.builder()
                .name(currencyCommand.name())
                .value(value)
                .currency(currencyCommand.currency())
                .date(LocalDate.now())
                .build();

        currencyRepository.save(currencyEntity);
        return currencyDto;
    }
}

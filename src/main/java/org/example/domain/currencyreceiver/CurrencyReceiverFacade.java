package org.example.domain.currencyreceiver;

import lombok.AllArgsConstructor;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;
import org.example.domain.currencyreceiver.dto.RequestDetailsDto;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class CurrencyReceiverFacade implements CurrencyReceiver{
    private final CurrencyRepository currencyRepository;
    private final RemoteCurrencyReceiver remoteCurrencyReceiver;
    private final Clock clock;


    public CurrencyDto getCurrentCurrency(CurrencyCommandDto currencyCommand) {
        BigDecimal value = remoteCurrencyReceiver.getCurrency(currencyCommand);
        CurrencyDto currencyDto = CurrencyDto.builder()
                .value(value)
                .build();

        Currency currencyEntity = Currency.builder()
                .name(currencyCommand.name())
                .value(value)
                .currency(currencyCommand.currency())
                .date(LocalDateTime.now(clock))
                .build();

        currencyRepository.save(currencyEntity);
        return currencyDto;
    }

    public List<RequestDetailsDto> getRequests() {
        List<Currency> requests = currencyRepository.findAll();

        return requests.stream()
                .map(currency -> RequestDetailsDto.builder()
                        .name(currency.getName())
                        .value(currency.getValue())
                        .currency(currency.getCurrency())
                        .date(currency.getDate())
                        .build())
                .toList();
    }
}

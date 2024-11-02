package org.example.infrastructure.currencyreceiver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.domain.currencyreceiver.CurrencyReceiver;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@AllArgsConstructor
public class CurrencyReceiverController {
    private final CurrencyReceiver currencyReceiverFacade;

    @PostMapping("/currencies/get-current-currency-value-command")
    public ResponseEntity<CurrencyResponseDto> getCurrency(
            @RequestBody @Valid CurrencyCommandRequestDto currencyDto
    ) {
        log.info("Start POST getCurrency");
        CurrencyCommandDto currencyCommandDto = CurrencyCommandDto.builder()
                .name(currencyDto.name())
                .currency(currencyDto.currency())
                .build();

        CurrencyDto currentCurrency = currencyReceiverFacade.getCurrentCurrency(currencyCommandDto);
        log.info("Stop POST getCurrency");
        return ResponseEntity.status(201).body(CurrencyResponseDto.builder()
                .value(currentCurrency.value())
                .build());
    }
}

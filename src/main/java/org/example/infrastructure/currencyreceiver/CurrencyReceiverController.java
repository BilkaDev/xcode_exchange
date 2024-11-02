package org.example.infrastructure.currencyreceiver;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.domain.currencyreceiver.CurrencyReceiver;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@AllArgsConstructor
public class CurrencyReceiverController {
    private final CurrencyReceiver currencyReceiverFacade;

    @PostMapping("/currencies/get-current-currency-value-command")
    public void getCurrency() {
        log.info("Start POST getCurrency");
        CurrencyCommandDto currencyCommandDto = new CurrencyCommandDto("Jan Nowak", "EUR");
        currencyReceiverFacade.getCurrentCurrency(currencyCommandDto);
        log.info("Stop POST getCurrency");
    }
}

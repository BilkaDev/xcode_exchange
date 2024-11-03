package org.example.domain.currencyreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class CurrencyReceiverConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }


    @Bean
    CurrencyReceiver currencyReceiverFacade(
            CurrencyRepository currencyRepository, RemoteCurrencyReceiver remoteCurrencyReceiver, Clock clock) {
        return new CurrencyReceiverFacade(currencyRepository, remoteCurrencyReceiver, clock);
    }
}

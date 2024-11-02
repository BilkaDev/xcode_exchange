package org.example.domain.currencyreceiver;

import java.time.Clock;

class CurrencyReceiverConfiguration {

    Clock clock() {
        return Clock.systemUTC();
    }


    CurrencyReceiverFacade currencyReceiverFacade(
            CurrencyRepository currencyRepository, RemoteCurrencyReceiver remoteCurrencyReceiver, Clock clock) {
        return new CurrencyReceiverFacade(currencyRepository, remoteCurrencyReceiver, clock);
    }
}

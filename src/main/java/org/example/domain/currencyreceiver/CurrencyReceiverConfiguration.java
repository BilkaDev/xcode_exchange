package org.example.domain.currencyreceiver;

public class CurrencyReceiverConfiguration {
    public CurrencyReceiverFacade currencyReceiverFacade(
            CurrencyRepository currencyRepository, RemoteCurrencyReceiver remoteCurrencyReceiver) {
        return new CurrencyReceiverFacade(currencyRepository, remoteCurrencyReceiver);
    }
}

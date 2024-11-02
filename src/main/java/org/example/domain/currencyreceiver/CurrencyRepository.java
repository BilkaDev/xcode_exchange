package org.example.domain.currencyreceiver;

import java.util.List;

public interface CurrencyRepository {
    Currency save(Currency currency);

    List<Currency> findAll();
}

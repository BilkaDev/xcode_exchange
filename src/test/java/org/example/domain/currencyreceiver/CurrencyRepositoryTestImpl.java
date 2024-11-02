package org.example.domain.currencyreceiver;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CurrencyRepositoryTestImpl implements CurrencyRepository {
    private final Map<String, Currency> currencyMap = new ConcurrentHashMap<>();


    @Override
    public Currency save(Currency currency) {
        currencyMap.put(String.valueOf(currencyMap.size()), currency);
        return null;
    }
}

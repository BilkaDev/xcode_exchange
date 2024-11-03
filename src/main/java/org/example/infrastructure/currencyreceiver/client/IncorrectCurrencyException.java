package org.example.infrastructure.currencyreceiver.client;

import org.example.infrastructure.config.httpexceptions.HttpException;
import org.springframework.http.HttpStatus;

public class IncorrectCurrencyException extends HttpException {
    public IncorrectCurrencyException() {
        super("Niepoprawna waluta", HttpStatus.BAD_REQUEST);
    }
}

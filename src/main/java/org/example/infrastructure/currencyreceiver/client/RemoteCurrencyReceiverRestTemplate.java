package org.example.infrastructure.currencyreceiver.client;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.domain.currencyreceiver.RemoteCurrencyReceiver;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class RemoteCurrencyReceiverRestTemplate implements RemoteCurrencyReceiver {

    private final RestTemplate restTemplate;
    private final String uri;

    @Override
    public BigDecimal getCurrency(CurrencyCommandDto currencyCommand) {
        log.info("Started fetching currencies value using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            final ResponseEntity<List<ExchangeRateTable>> response = makeGetRequest(requestEntity);
            response.getBody();
            List<ExchangeRate> exchangeRates = getExchangeRates(response);

            Optional<Double> first = exchangeRates.stream()
                    .filter(exchangeRate -> exchangeRate.getCode().equals(currencyCommand.currency()))
                    .map(ExchangeRate::getMid)
                    .findFirst();
            return first.map(BigDecimal::valueOf).orElseThrow(IncorrectCurrencyException::new);
        } catch (ResourceAccessException | IllegalArgumentException e) {
            log.error("Error while fetching currency using http client: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<ExchangeRate> getExchangeRates(ResponseEntity<List<ExchangeRateTable>> response) {
        List<ExchangeRateTable> exchangeRateTables = response.getBody();
        if (exchangeRateTables == null) {
            log.error("Response Body was null");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        log.info("Success Response Body Returned: " + response);
        return exchangeRateTables.stream()
                .map(ExchangeRateTable::getRates)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    private ResponseEntity<List<ExchangeRateTable>> makeGetRequest(HttpEntity<HttpHeaders> requestEntity) {
        final String url = UriComponentsBuilder.fromHttpUrl(uri)
                .toUriString();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}

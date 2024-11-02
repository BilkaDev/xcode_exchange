package org.example.http.currencyreceiver;

import org.example.infrastructure.currencyreceiver.client.RemoteCurrencyReceiverClientConfig;
import org.example.infrastructure.currencyreceiver.client.RemoteCurrencyReceiverRestTemplate;
import org.springframework.web.client.RestTemplate;

public class RemoteCurrencyReceiverRestTemplateConfigTest extends RemoteCurrencyReceiverClientConfig {

    public RemoteCurrencyReceiverRestTemplate remoteCurrencyReceiverClient(String url) {
        RestTemplate restTemplate = restTemplate(
                1000,
                1000,
                restTemplateResponseErrorHandler()
        );
        return new RemoteCurrencyReceiverRestTemplate(restTemplate, url);
    }

}

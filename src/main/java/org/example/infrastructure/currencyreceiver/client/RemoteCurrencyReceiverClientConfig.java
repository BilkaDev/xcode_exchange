package org.example.infrastructure.currencyreceiver.client;


import org.example.domain.currencyreceiver.RemoteCurrencyReceiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RemoteCurrencyReceiverClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${xcode.currency-receiver.http.client.config.connectionTimeout:1000}") long connectionTimeout,
                                     @Value("${xcode.currency-receiver.client.config.readTimeout:1000}") long readTimeout,
                                     RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public RemoteCurrencyReceiver remoteCurrencyReceiverClient(RestTemplate restTemplate,
                                                               @Value("${xcode.currency-receiver.http.client.config.uri}") String uri) {
        return new RemoteCurrencyReceiverRestTemplate(restTemplate, uri);
    }
}

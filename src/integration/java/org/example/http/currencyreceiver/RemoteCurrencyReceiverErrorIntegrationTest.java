package org.example.http.currencyreceiver;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.example.domain.currencyreceiver.RemoteCurrencyReceiver;
import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.infrastructure.config.httpexceptions.HttpException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class RemoteCurrencyReceiverErrorIntegrationTest {
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    RemoteCurrencyReceiver remoteCurrencyReceiver = new RemoteCurrencyReceiverRestTemplateConfigTest().remoteCurrencyReceiverClient(
            "http://localhost:" + wireMockServer.getPort()
    );


    @Test
    void should_throw_exception_500_when_fault_connection_reset_by_peer() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_500_when_fault_empty_response() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE)));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_500_when_fault_malformed_response_chunk() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_500_when_fault_random_data_then_close() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_204_when_status_is_204_no_content() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                """.trim()
                        )));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    void should_throw_exception_500_when_response_delay_is_5000_ms_and_client_has_1000ms_read_timeout() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                """.trim()
                        )
                        .withFixedDelay(5000)));

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));


        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_404_when_http_service_returning_not_found_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.SC_NOT_FOUND))
        );

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }

    @Test
    void should_throw_exception_401_when_http_service_returning_unauthorized_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.SC_UNAUTHORIZED))
        );

        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "USD");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }

    @Test
    void should_throw_exception_400_when_currency_code_is_invalid() {
        // given
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                [
                                  {
                                    "table": "A",
                                    "no": "213/A/NBP/2024",
                                    "effectiveDate": "2024-10-31",
                                    "rates": [
                                      {
                                        "currency": "bat (Tajlandia)",
                                        "code": "THB",
                                        "mid": 0.1187
                                      }
                                    ]
                                  }
                                ]""".trim()
                        )));
        // when
        CurrencyCommandDto currencyCommand = new CurrencyCommandDto("Jan Nowak", "EUR");
        Throwable throwable = catchThrowable(() -> remoteCurrencyReceiver.getCurrency(currencyCommand));

        // then
        assertThat(throwable).isInstanceOf(HttpException.class);
        assertThat(throwable.getMessage()).isEqualTo("invalid currency code");
    }
}
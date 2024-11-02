package org.example.domain.currencyreceiver;

import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;
import org.example.domain.currencyreceiver.dto.RequestDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CurrencyReceiverFacadeTest {
    private CurrencyReceiver currencyReceiverFacade;

    @BeforeEach
    public void setUp() {
        CurrencyRepository currencyRepository = new CurrencyRepositoryTestImpl();
        RemoteCurrencyReceiver remoteCurrencyReceiver = new RemoteCurrencyReceiverTestImpl();
        AdjustableClock clock = new AdjustableClock(
                LocalDateTime.of(2024, 11, 2, 10, 0, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );

        currencyReceiverFacade = new CurrencyReceiverConfiguration()
                .currencyReceiverFacade(
                        currencyRepository,
                        remoteCurrencyReceiver,
                        clock
                );
    }

    @Test
    public void should_return_when_user_gave_correct_currency() {
        // given
        CurrencyCommandDto currencyCommandDto = new CurrencyCommandDto("Jan Nowak", "EUR");

        // when
        CurrencyDto result = currencyReceiverFacade.getCurrentCurrency(currencyCommandDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualTo("0.12345");
    }

    @Test
    public void should_return_empty_array_when_request_is_null() {
        // when
        List<RequestDetailsDto> requestsDto = currencyReceiverFacade.getRequests();

        // then
        assertThat(requestsDto).isNotNull();
        assertThat(requestsDto.size()).isEqualTo(0);
    }

    @Test
    public void should_return_array_with_one_element_when_request_is_not_null() {
        // given
        CurrencyCommandDto currencyCommandDto = new CurrencyCommandDto("Jan Nowak", "EUR");
        currencyReceiverFacade.getCurrentCurrency(currencyCommandDto);

        // when
        List<RequestDetailsDto> requestsDto = currencyReceiverFacade.getRequests();

        System.out.println(requestsDto);
        // then
        assertThat(requestsDto).isNotNull();
        assertThat(requestsDto.size()).isEqualTo(1);
        assertThat(requestsDto.get(0).name()).isEqualTo("Jan Nowak");
        assertThat(requestsDto.get(0).currency()).isEqualTo("EUR");
        assertThat(requestsDto.get(0).value()).isEqualTo("0.12345");
        assertThat(requestsDto.get(0).date())
                .isEqualTo(LocalDateTime.of(2024, 11, 2, 10, 0, 0));
    }
}
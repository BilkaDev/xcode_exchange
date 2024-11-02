package org.example.domain.currencyreceiver;

import org.example.domain.currencyreceiver.dto.CurrencyCommandDto;
import org.example.domain.currencyreceiver.dto.CurrencyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CurrencyReceiverFacadeTest {
    private final CurrencyRepository currencyRepository = new CurrencyRepositoryTestImpl();
    private final RemoteCurrencyReceiver remoteCurrencyReceiver = new RemoteCurrencyReceiverTestImpl();

    @Test
    public void should_return_when_user_gave_correct_currency() {
        // given
        CurrencyReceiverFacade currencyReceiverFacade = new CurrencyReceiverConfiguration()
                .currencyReceiverFacade(
                        currencyRepository,
                        remoteCurrencyReceiver
                );
        CurrencyCommandDto currencyCommandDto = new CurrencyCommandDto("Jan Nowak", "EUR");

        // when
        CurrencyDto result = currencyReceiverFacade.getCurrentCurrency(currencyCommandDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualTo("0.12345");
    }
}
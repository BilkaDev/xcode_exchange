package org.example.features;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserGetCurrencyRatesIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_currency_rates_and_all_request_details() throws Exception {
        // step 1.external service returns currency rates
        // given
        String mockRequestBody = """
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
                ]""".trim();
        wireMockServer.stubFor(WireMock.get("/")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockRequestBody)
                ));


        // step 2. when user input  POST /currencies/get-current-currency-value-command on date on date 02.11.2024 system returns status 201 and  { value: 4.2954 }
        // given & when
        ResultActions successPostCurrencies = mockMvc.perform(post("/currencies/get-current-currency-value-command")
                .content("""
                        {
                        "name": "Jan Nowak",
                        "currency": "THB"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        successPostCurrencies
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "value":0.1187
                        }
                        """.trim()));


//        1.1 if a non-existent currency is specified, the system returns status: 400 and {
//            "error": "Invalid currency code"
//        }

        // given & when
        ResultActions invalidCurrencyCode = mockMvc.perform(post("/currencies/get-current-currency-value-command")
                .content("""
                        {
                        "name": "Jan Nowak",
                        "currency": "xxx"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        invalidCurrencyCode
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"messages":["invalid currency code"],"status":"BAD_REQUEST"}
                        """.trim()));


//        2. when user input GET /currencies/requests system returns status 200 and [{
//                currency: ”EUR”,
//        name: ”Jan Nowak”,
//        date: “2024-11-02T11:00:00.000Z”,
//        value: 4.2954
//}]
        // given & when
        ResultActions successGetCurrenciesRequests = mockMvc.perform(get("/currencies/requests")
                .content("""
                        {
                        "name": "Jan Nowak",
                        "currency": "THB"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        successGetCurrenciesRequests
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "currency": "THB",
                            "name": "Jan Nowak",
                            "date": "2024-11-02T11:00:00",
                            "value": 0.1187
                        }]
                        """.trim()));
    }
}

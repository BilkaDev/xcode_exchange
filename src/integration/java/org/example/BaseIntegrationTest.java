package org.example;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.example.domain.currencyreceiver.AdjustableClock;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = {XcodeApplication.class, IntegrationConfiguration.class})
@ActiveProfiles("integration")
@AutoConfigureMockMvc
public class BaseIntegrationTest {
    public static final String WIREMOCK_HOST = "http://localhost";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public AdjustableClock clock;

    @Container
    public final static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"));

    @DynamicPropertySource
    public static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();
}
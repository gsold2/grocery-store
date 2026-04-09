package com.github.gsold2.manager.configuration;

import com.github.gsold2.manager.client.ProductRestClient;
import com.github.gsold2.manager.client.ProductRestClientImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Configuration
@AllArgsConstructor
public class RestClientConfiguration {

    @Bean
    public ProductRestClient productRestClientImpl(@Value("${service.catalogue.uri:http://localhost:8081}") String serviceBaseUri,
                                                   @Value("${service.catalogue.username:user}") String username,
                                                   @Value("${service.catalogue.password:password}") String password) {
        String credentials = "%s:%s".formatted(username, password);
        String encodedCredentials = "Basic %s".formatted(Base64.getEncoder().encodeToString(credentials.getBytes()));
        return new ProductRestClientImpl(
                RestClient.builder()
                        .baseUrl(serviceBaseUri)
                        .defaultHeader(HttpHeaders.AUTHORIZATION, encodedCredentials)
                        .build()
        );
    }
}

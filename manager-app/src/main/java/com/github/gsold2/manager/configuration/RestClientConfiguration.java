package com.github.gsold2.manager.configuration;

import com.github.gsold2.manager.client.ProductRestClient;
import com.github.gsold2.manager.client.ProductRestClientImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@AllArgsConstructor
public class RestClientConfiguration {

    @Bean
    public ProductRestClient productRestClientImpl(@Value("${service.catalogue.uri:http://localhost:8081}") String serviceBaseUri) {
        return new ProductRestClientImpl(
                RestClient.builder()
                        .baseUrl(serviceBaseUri)
                        .build()
        );
    }
}

package com.github.gsold2.manager.configuration;

import com.github.gsold2.manager.client.ProductRestClient;
import com.github.gsold2.manager.client.ProductRestClientImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
@AllArgsConstructor
public class RestClientConfiguration {

    @Bean
    public ProductRestClient productRestClientImpl(@Value("${service.catalog.uri:http://localhost:8081}") String serviceBaseUri,
                                                   @Value("${service.catalog.username:user}") String userName,
                                                   @Value("${service.catalog.password:password}") String password) {
        return new ProductRestClientImpl(
                RestClient.builder()
                        .baseUrl(serviceBaseUri)
                        .requestInterceptor(new BasicAuthenticationInterceptor(userName, password))
                        .build()
        );
    }
}

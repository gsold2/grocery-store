package com.github.gsold2.manager.client;

import com.github.gsold2.manager.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRestClientImpl implements ProductRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;

    @Override
    public List<Product> getAll() {
        return restClient.get()
                .uri("/api/products")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Optional<Product> get(int id) {
        try {
            return Optional.ofNullable(restClient.get()
                    .uri("/api/products/{id}", id)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(int id) {
        try {
            restClient.delete()
                    .uri("/api/products/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException("errors.product.not_found");
        }
    }

    @Override
    public void update(Product product) {
        restClient.patch()
                .uri("/api/products/{id}", product.getId())
                .body(product)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Product create(Product product) {
        return restClient.post()
                .uri("/api/products")
                .body(product)
                .retrieve()
                .body(Product.class);
    }
}

package com.github.gsold2.catalog.repository;

import com.github.gsold2.catalog.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private final Map<Integer, Product> products = Collections.synchronizedMap(new HashMap<>());

    public InMemoryProductRepositoryImpl() {
        IntStream.range(1, 4)
                .forEach(number -> {
                    Product product = new Product(number, "%d product title".formatted(number), "%d items description".formatted(number));
                    products.put(number, product);
                });
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<Product> find(int id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public boolean delete(int id) {
        return products.remove(id) != null;
    }

    @Override
    public Product create(Product product) {
        if (products.isEmpty()) {
            product.setId(1);
        } else if (product.getId() == null) {
            product.setId(products.keySet().stream().max(Integer::compareTo).get() + 1);
        }
        products.put(product.getId(), product);

        return product;
    }
}

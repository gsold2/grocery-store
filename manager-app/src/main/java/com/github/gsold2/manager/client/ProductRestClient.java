package com.github.gsold2.manager.client;

import com.github.gsold2.manager.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> getList(String filter);

    Optional<Product> get(int id);

    void delete(int id);

    void update(Product product);

    Product create(Product product);
}

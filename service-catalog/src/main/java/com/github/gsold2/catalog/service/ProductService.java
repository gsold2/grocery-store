package com.github.gsold2.catalog.service;

import com.github.gsold2.catalog.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> find(int id);

    boolean delete(int id);

    Product create(Product product);

    void update(Product product);
}

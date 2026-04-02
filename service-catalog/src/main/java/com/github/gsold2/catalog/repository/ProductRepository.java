package com.github.gsold2.catalog.repository;

import com.github.gsold2.catalog.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> find(int id);

     boolean delete(int id);

    Product create(Product product);

    void update(Product product);
}

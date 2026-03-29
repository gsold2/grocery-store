package com.github.gsold2.manager.repository;

import com.github.gsold2.manager.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> find(int id);

     boolean delete(int id);

    Product save(Product product);
}

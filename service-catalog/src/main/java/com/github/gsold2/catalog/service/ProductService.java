package com.github.gsold2.catalog.service;

import com.github.gsold2.catalog.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product find(int id);

    void delete(int id);

    Product create(Product product);

    void update(Product product);
}

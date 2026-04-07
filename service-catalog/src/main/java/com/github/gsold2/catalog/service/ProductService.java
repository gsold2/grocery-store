package com.github.gsold2.catalog.service;

import com.github.gsold2.catalog.model.Product;

public interface ProductService {

    Iterable<Product> getList(String filter);

    Product find(int id);

    void delete(int id);

    Product create(Product product);

    void update(Product product);
}

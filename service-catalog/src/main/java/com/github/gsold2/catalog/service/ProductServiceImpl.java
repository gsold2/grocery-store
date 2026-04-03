package com.github.gsold2.catalog.service;

import com.github.gsold2.catalog.model.Product;
import com.github.gsold2.catalog.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product find(int id) {
        return repository.find(id).orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
    }

    @Override
    public void delete(int id) {
        if (!repository.delete(id)) {
            throw new NoSuchElementException("errors.product.not_found");
        }
    }

    @Override
    public Product create(Product product) {
        return repository.create(product);
    }

    @Override
    public void update(Product product) {
        repository.find(product.getId())
                .ifPresentOrElse(p -> {
                    p.setTitle(product.getTitle());
                    p.setDescription(product.getDescription());
                }, () -> {
                    throw new NoSuchElementException("errors.product.not_found");
                });
    }
}

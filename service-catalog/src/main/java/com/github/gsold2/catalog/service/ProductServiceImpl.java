package com.github.gsold2.catalog.service;

import com.github.gsold2.catalog.model.Product;
import com.github.gsold2.catalog.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public Iterable<Product> getList(String filter) {
        if (filter != null && !filter.isBlank()) {
            return repository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return repository.findAll();
        }
    }

    @Override
    public Product find(int id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.delete(find(id));

    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        Product existedProduct = find(product.getId());
        existedProduct.setTitle(product.getTitle());
        existedProduct.setDescription(product.getDescription());
    }
}

package com.github.gsold2.manager.service;

import com.github.gsold2.manager.model.Product;
import com.github.gsold2.manager.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> find(int id) {
        return repository.find(id);
    }

    @Override
    public boolean delete(int id) {
       return repository.delete(id);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }
}

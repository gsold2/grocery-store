package com.github.gsold2.catalog.controller;

import com.github.gsold2.catalog.model.Product;
import com.github.gsold2.catalog.payload.ProductPayload;
import com.github.gsold2.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping()
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product get(@PathVariable(value = "id") int id) {
        return productService.find(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        productService.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ProductPayload product, BindingResult bindingResult, @PathVariable int id) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        productService.update(new Product(id, product.getTitle(), product.getDescription()));
    }

    @PostMapping()
    public ResponseEntity<Product> create(@Valid @RequestBody ProductPayload product, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        Product created = productService.create(new Product(null, product.getTitle(), product.getDescription()));
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/api/products/{id}")
                        .build(Map.of("id", created.getId())))
                .body(created);

    }
}

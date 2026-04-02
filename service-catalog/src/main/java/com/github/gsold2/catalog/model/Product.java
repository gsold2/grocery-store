package com.github.gsold2.catalog.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    Integer id;

    @NotNull(message = "{products.errors.title_is_null}")
    @Size(min = 3, max = 50, message = "{products.errors.title_size_is_invalid}")
    String title;

    @Size(max = 1000, message = "{products.errors.description_size_is_invalid}")
    String description;
}

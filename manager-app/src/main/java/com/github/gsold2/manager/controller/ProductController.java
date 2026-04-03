package com.github.gsold2.manager.controller;

import com.github.gsold2.manager.client.ProductRestClient;
import com.github.gsold2.manager.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductRestClient productRestClient;
    private final MessageSource messageSource;


    @GetMapping("list")
    public String getAll(Model model) {
        model.addAttribute("products", productRestClient.getAll());
        return "list";
    }

    @GetMapping("{id}")
    public String get(@PathVariable(value = "id") int id, Model model) {
        Product product = productRestClient.get(id)
                .orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable(value = "id") int id) {
        productRestClient.delete(id);
        return "redirect:/products/list";
    }

    @GetMapping("create")
    public String create() {
        return "edit";
    }

    @GetMapping("{id}/edit")
    public String update(@PathVariable(value = "id") int id, Model model) {
        Product product = productRestClient.get(id)
                .orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("edit")
    public String edit(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
            return "edit";
        } else {
            if (product.getId() == null) {
                Product newProduct = productRestClient.create(product);
                model.addAttribute("product", newProduct);
            } else {
                productRestClient.update(product);
                model.addAttribute("product", product);
            }

            return "redirect:/products/list";
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandle(NoSuchElementException exception, Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute(
                "error",
                messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale));
        return "error_page";
    }
}

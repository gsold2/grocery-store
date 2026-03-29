package com.github.gsold2.manager.controller;

import com.github.gsold2.manager.model.Product;
import com.github.gsold2.manager.service.ProductService;
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

    private final ProductService productService;
    private final MessageSource messageSource;


    @GetMapping("list")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "list";
    }

    @GetMapping("{id}")
    public String getProduct(@PathVariable(value = "id") int id, Model model) {
        Product product = productService.find(id)
                .orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("{id}/delete")
    public String deleteProduct(@PathVariable(value = "id") int id) {
        if (!productService.delete(id)) {
            throw new NoSuchElementException("errors.product.not_found");
        }
        return "redirect:/products/list";
    }

    @GetMapping("create")
    public String createProduct() {
        return "edit";
    }

    @GetMapping("{id}/edit")
    public String editProduct(@PathVariable(value = "id") int id, Model model) {
        Product product = productService.find(id)
                .orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("edit")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
            return "edit";
        } else {
            Product newProduct = productService.save(product);
            model.addAttribute("product", newProduct);
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

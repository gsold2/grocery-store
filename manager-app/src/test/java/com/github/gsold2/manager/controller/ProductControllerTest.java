package com.github.gsold2.manager.controller;

import com.github.gsold2.manager.client.BadRequestException;
import com.github.gsold2.manager.client.ProductRestClient;
import com.github.gsold2.manager.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Module tests for the ProductController")
class ProductControllerTest {

    @Mock
    private ProductRestClient productRestClient;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ProductController productController;

    @Test
    @DisplayName("Method 'edit' create a new product and redirect to the list page")
    void edit_RequestValid_RedirectToListPage() {
        //given
        Product product = new Product(null, "title", "description");
        Model model = new ExtendedModelMap();

        doReturn(new Product(1, "title", "description"))
                .when(productRestClient)
                .create(new Product(null, "title", "description"));

        //when
        String result = productController.edit(product, model);

        //then
        assertEquals("redirect:/products/list", result);
        Mockito.verify(productRestClient).create(product);
        Mockito.verifyNoMoreInteractions(productRestClient);
    }

    @Test
    @DisplayName("Method 'edit' doesn't create a new product, throw an exception and redirect to the edit page with errors")
    void edit_RequestInValid_ThrowException_RedirectToEditPage() {
        //given
        Product product = new Product(null, " ", "description");
        Model model = new ExtendedModelMap();

        List<String> errors = List.of("The 'title' size must be between 3 and 50");
        doThrow(new BadRequestException(errors))
                .when(productRestClient)
                .create(new Product(null, " ", "description"));

        //when
        String result = productController.edit(product, model);

        //then
        assertEquals("edit", result);
        assertEquals(product, model.getAttribute("product"));
        assertEquals(errors, model.getAttribute("errors"));
        Mockito.verify(productRestClient).create(new Product(null, " ", "description"));
        Mockito.verifyNoMoreInteractions(productRestClient);
    }
}
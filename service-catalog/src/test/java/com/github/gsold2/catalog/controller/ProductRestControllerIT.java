package com.github.gsold2.catalog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest()
@AutoConfigureMockMvc
class ProductRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/sql/products.sql")
    void getList_ReturnsProductsList() throws Exception {

        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products")
                .with(user("user").roles("USER"));

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [{"id":1,"title":"title 1","description":"description 1"},{"id":2,"title":"title 2","description":"description 2"},{"id":3,"title":"title 3","description":"description 4"}]
                                """)
                );
    }
}
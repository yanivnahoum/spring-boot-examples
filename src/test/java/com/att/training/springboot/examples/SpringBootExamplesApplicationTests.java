package com.att.training.springboot.examples;

import com.att.training.springboot.examples.api.ProductController;
import com.att.training.springboot.examples.api.ProductNotFoundException;
import com.att.training.springboot.examples.api.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SpringBootExamplesApplicationTests {

    @Test
    void contextLoads() {
    }

}

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    void givenMissingProductId_whenGetProductById_thenRespondWith404() throws Exception {
        int missingProductId = 100;
        when(productService.getById(missingProductId))
                .thenThrow(new ProductNotFoundException(String.valueOf(missingProductId)));

        mockMvc.perform(get("/products/{id}", missingProductId)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


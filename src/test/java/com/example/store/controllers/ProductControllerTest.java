package com.example.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.store.config.JwtService;
import com.example.store.dao.ProductDao;
import com.example.store.entities.Product;
import com.example.store.services.ProductService;
import com.example.store.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductDao productDao;

  @MockBean
  private ProductService productService;

  @MockBean
  private UserDetailsService userDetailsService;

  @MockBean
  private JwtService jwtService;

  @Test
  void testAddProduct() throws Exception {
    // Given
    Product product = createProduct();
    when(productService.addProduct(any(Product.class))).thenReturn(product);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/store/product")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken("email", List.of("ROLE_USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(),
        Product.class);

    // Assert
    assertNotNull(actualProduct.getId(), "Should not be null");
    assertEquals(product.getName(), actualProduct.getName(), "Should have the same value");
    assertEquals(product.getDescription(), actualProduct.getDescription(), "Should have the same value");
    assertEquals(product.getPrice(), actualProduct.getPrice(), "Should have the same value");
    assertEquals(product.getInitialQuantity(), actualProduct.getInitialQuantity(), "Should have the same value");
    assertEquals(product.getQuantity(), actualProduct.getQuantity(), "Should have the same value");
  }

  private static Product createProduct() {
    return Product.builder()
            .id(1L)
            .name("Shampoo")
            .description("Body care")
            .price(25.99)
            .initialQuantity(100)
            .quantity(56).build();
  }
}

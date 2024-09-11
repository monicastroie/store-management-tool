package com.example.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.store.config.JwtService;
import com.example.store.entities.Product;
import com.example.store.exceptions.ResourceNotFoundException;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  private static final Long ID = 1L;
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  @MockBean
  private UserDetailsService userDetailsService;

  @MockBean
  private JwtService jwtService;

  @Test
  @WithMockUser(roles = "USER")
  void testAddProduct() throws Exception {
    // Given
    Product product = createProduct();
    when(productService.addProduct(any(Product.class))).thenReturn(product);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/store/product")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken("email", List.of("USER")))
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

  @Test
  @WithMockUser(roles = "USER")
  void testGetProductById() throws Exception {
    //Given
    Product product = createProduct();
    when(productService.getProduct(ID)).thenReturn(product);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/store/product/{id}", ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken("email", List.of("USER")))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(),
        Product.class);

    // Assert
    assertNotNull(product);
    assertNotNull(actualProduct.getId());
    assertEquals(product.getName(), actualProduct.getName(), "Should have the same value");
    assertEquals(product.getDescription(), actualProduct.getDescription(), "Should have the same value");
    assertEquals(product.getPrice(), actualProduct.getPrice(), "Should have the same value");
    assertEquals(product.getInitialQuantity(), actualProduct.getInitialQuantity(), "Should have the same value");
    assertEquals(product.getQuantity(), actualProduct.getQuantity(), "Should have the same value");
  }

  @Test
  @WithMockUser(roles = "USER")
  void testGetProductById_NotFound() throws Exception {
    //Given
    when(productService.getProduct(ID)).thenThrow(ResourceNotFoundException.class);

    // Then
    mockMvc.perform(
          MockMvcRequestBuilders.get("/api/v1/store/product/{id}", ID)
              .header(HttpHeaders.AUTHORIZATION,
                  "Bearer " + JwtUtil.generateToken("email", List.of("USER")))
              .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNotFound())
      .andReturn();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testDeleteProduct() throws Exception {
    //Given
    Product product = createProduct();
    when(productService.getProduct(ID)).thenReturn(product);

    // Then
    mockMvc.perform(
          MockMvcRequestBuilders.delete("/api/v1/store/delete/product/{id}", ID)
              .header(HttpHeaders.AUTHORIZATION,
                  "Bearer " + JwtUtil.generateToken("email", List.of("ADMIN")))
              .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
  }

  @Test
  @WithMockUser(roles = "USER")
  void testUpdateProductById() throws Exception {
    //Given
    Product newProduct = createProduct();
    newProduct.setPrice(30.50);
    when(productService.updateProductById(newProduct, ID)).thenReturn(newProduct);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/store/update/product/{id}", ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken("email", List.of("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newProduct)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(),
        Product.class);

    // Assert
    assertNotNull(actualProduct.getId());
    assertEquals(newProduct.getPrice(), actualProduct.getPrice(), "Should be the same values.");
  }

  @Test
  @WithMockUser(roles = "USER")
  void testUpdateProductByPrice() throws Exception {
    //Given
    Double newPrice = 30.50;
    Product product = createProduct();
    product.setPrice(newPrice);
    when(productService.updateProductByPrice(ID, product)).thenReturn(product);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/store/update/product/{id}/price", ID, newPrice)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken("email", List.of("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(),
        Product.class);

    // Assert
    assertNotNull(actualProduct.getId());
    assertEquals(newPrice, actualProduct.getPrice(), "Should be the same values.");
  }

  private static Product createProduct() {
    return Product.builder()
            .id(ID)
            .name("Shampoo")
            .description("Body care")
            .price(25.99)
            .initialQuantity(100)
            .quantity(56).build();
  }
}

package com.example.store.controllers;

import com.example.store.entities.Product;
import com.example.store.services.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class ProductController {
  private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
  private final ProductService productService;

  @PostMapping("/product")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
    LOGGER.debug("Creating a product with following information {}", product);
    return ResponseEntity.ok().body(productService.addProduct(product));
  }

  @GetMapping("/products")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<Product>> getProducts() {
    LOGGER.debug("Retrieving the list of products.");
    return ResponseEntity.ok().body(productService.getProducts());
  }

  @GetMapping("/product/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
    LOGGER.debug("Retrieving user with ID {}", id);
    return ResponseEntity.ok().body(productService.getProduct(id));
  }

  @DeleteMapping("/delete/product/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
    LOGGER.debug("Deleting the product with ID {}.", id);
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/update/product/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> updateProductById(@RequestBody Product product, @PathVariable("id") Long id) {
    LOGGER.debug("Updating the product with ID {}.", id);
    return ResponseEntity.ok().body(productService.updateProductById(product, id));
  }

  @PatchMapping("/update/product/{id}/quantity/{quantity}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> updateProductByQuantity(@PathVariable("id") Long id,
      @PathVariable("quantity") Integer quantity) {
    LOGGER.debug("Updating the quantity to {} for the product with ID {}.", quantity, id);
    return ResponseEntity.ok().body(productService.updateProductByQuantity(id, quantity));
  }

  @PatchMapping("/update/product/{id}/price/{price}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> updateProductByPrice(@PathVariable("id") Long id,
      @PathVariable("price") Double price) {
    LOGGER.debug("Updating the price to {} for product with ID {}.", price, id);
    return ResponseEntity.ok().body(productService.updateProductByPrice(id, price));
  }
}

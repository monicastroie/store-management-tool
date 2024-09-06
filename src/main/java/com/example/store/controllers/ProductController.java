package com.example.store.controllers;

import com.example.store.dao.ProductDao;
import com.example.store.entities.Product;
import com.example.store.services.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  private final ProductService productService;
  private final ProductDao productDao;

  @PostMapping("/product")
  public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
    return ResponseEntity.ok().body(productService.addProduct(product));
  }

  @GetMapping("/products")
  public ResponseEntity<List<Product>> getProducts() {
    return ResponseEntity.ok().body(productService.getProducts());
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<Optional<Product>> getProduct(@PathVariable("id") Long id) {
    Optional<Product> actualProduct = productDao.findById(id);
    if(actualProduct.isPresent()){
      return ResponseEntity.ok().body(actualProduct);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/delete/product/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
    Optional<Product> actualProduct = productDao.findById(id);
    if(actualProduct.isPresent()){
      productService.deleteProduct(actualProduct.get().getId());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/update/product/{id}")
  public ResponseEntity<Product> updateProductById(@RequestBody Product product, @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(productService.updateProductById(product, id));
  }

  @PatchMapping("/update/product/{id}/quantity/{quantity}")
  public ResponseEntity<Product> updateProductByQuantity(@PathVariable("id") Long id,
      @PathVariable("quantity") Integer quantity) {
    return ResponseEntity.ok().body(productService.updateProductByQuantity(id, quantity));
  }

  @PatchMapping("/update/product/{id}/price/{price}")
  public ResponseEntity<Product> updateProductByPrice(@PathVariable("id") Long id,
      @PathVariable("price") Double price) {
    return ResponseEntity.ok().body(productService.updateProductByPrice(id, price));
  }
}

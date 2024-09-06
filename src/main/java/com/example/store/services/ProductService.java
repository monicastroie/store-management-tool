package com.example.store.services;

import com.example.store.entities.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

  Product addProduct(Product product);

  List<Product> getProducts();

  Optional<Product> getProduct(Long id);

  void deleteProduct(Long id);

  Product updateProductById(Product product, Long id);

  Product updateProductByQuantity(Long id, Integer quantity);

  Product updateProductByPrice(Long id, Double price);
}

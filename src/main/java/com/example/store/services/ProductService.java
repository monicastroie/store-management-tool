package com.example.store.services;

import com.example.store.entities.Product;
import java.util.List;

/**
 * Represents the service used for product operations
 */
public interface ProductService {

  /**
   * Method for inserting a product in database
   * @param product
   */
  Product addProduct(Product product);

  /**
   * Method for retrieving all the products from database
   */
  List<Product> getProducts();

  /**
   * Method for retrieving a product from database using id value
   * @param id
   */
  Product getProduct(Long id);

  /**
   * Method for deleting a given product from database
   * @param id
   */
  void deleteProduct(Long id);

  /**
   * Method for updating a product from database
   * @param product
   * @param id
   */
  Product updateProductById(Product product, Long id);

  /**
   * Method for updating the quantity of a given product
   * @param id
   * @param quantity
   */
  Product updateProductByQuantity(Long id, Integer quantity);

  /**
   * Method for updating the price of a given product
   * @param id
   * @param price
   */
  Product updateProductByPrice(Long id, Double price);
}

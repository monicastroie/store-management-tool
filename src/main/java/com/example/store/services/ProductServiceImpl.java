package com.example.store.services;

import com.example.store.dao.ProductDao;
import com.example.store.entities.Product;
import com.example.store.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductDao productDao;

  public Product addProduct(Product product) {
    return productDao.save(product);
  }

  public List<Product> getProducts() {
    return (List<Product>) productDao.findAll();
  }

  public Optional<Product> getProduct(Long id) {
    return productDao.findById(id);
  }

  public void deleteProduct(Long id) {
    productDao.deleteById(id);
  }

  public Product updateProductById(Product product, Long id) {
    Product actualProduct = productDao.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

      actualProduct.setName(product.getName());
      actualProduct.setDescription(product.getDescription());
      actualProduct.setPrice(product.getPrice());
      actualProduct.setQuantity(product.getQuantity());
      actualProduct.setInitialQuantity(product.getInitialQuantity());
      actualProduct.setStartOfWeek(product.getStartOfWeek());
      actualProduct.setEndOfWeek(product.getEndOfWeek());

      return productDao.save(actualProduct);
  }

  public Product updateProductByQuantity(Long id, Integer quantity) {
    Product actualProduct = productDao.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

    actualProduct.setQuantity(quantity);
    return productDao.save(actualProduct);
  }

  public Product updateProductByPrice(Long id, Double price) {
    Product actualProduct = productDao.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

    actualProduct.setPrice(price);
    return productDao.save(actualProduct);
  }

}
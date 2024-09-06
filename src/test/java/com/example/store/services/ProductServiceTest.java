package com.example.store.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.store.dao.ProductDao;
import com.example.store.entities.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  private static final Long FIRST_ID = 1L;
  private static final Long SECOND_ID = 2L;
  private static final Double FIRST_PRICE = 23.99;
  private static final Double SECOND_PRICE = 12.33;

  @Mock
  private ProductDao productDao;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void testAddProduct() {
    // Given
    Product product = createProduct(FIRST_ID, FIRST_PRICE);

    // Then
    when(productDao.save(product)).thenReturn(product);
    Product actualProduct = productService.addProduct(product);

    //Assert
    assertEquals(product, actualProduct, "Should have the same values.");
  }

  @Test
  void testGetProducts() {
    // Given
    List<Product> products = List.of(createProduct(FIRST_ID, FIRST_PRICE), createProduct(SECOND_ID, SECOND_PRICE));

    // Then
    when(productDao.findAll()).thenReturn(products);
    List<Product> actualProducts = productService.getProducts();

    // Assert
    assertEquals(products, actualProducts, "Should have the same values.");
  }

  @Test
  void testGetProduct() {
    // Given
    Product product = createProduct(FIRST_ID, FIRST_PRICE);

    // Then
    when(productDao.findById(FIRST_ID)).thenReturn(Optional.of(product));
    Optional<Product> actualProduct = productService.getProduct(FIRST_ID);

    // Assert
    assertEquals(product, actualProduct.get(), "Should have the same values.");
  }

  @Test
  void testUpdateProductById() {
    // Given
    Product product = createProduct(FIRST_ID, FIRST_PRICE);
    Product updatedProduct = createProduct(FIRST_ID, SECOND_PRICE);

    // When
    when(productDao.findById(FIRST_ID)).thenReturn(Optional.of(product));
    when(productDao.save(updatedProduct)).thenReturn(updatedProduct);
    Product actualProduct = productService.updateProductById(updatedProduct, FIRST_ID);

    // Assert
    assertEquals(updatedProduct, actualProduct, "Should have the same values.");
  }

  @Test
  void testUpdateProductByPrice() {
    // Given
    Product product = createProduct(FIRST_ID, FIRST_PRICE);
    Product updatedProduct = createProduct(FIRST_ID, SECOND_PRICE);

    // When
    when(productDao.findById(FIRST_ID)).thenReturn(Optional.of(product));
    when(productDao.save(updatedProduct)).thenReturn(updatedProduct);
    Product actualProduct = productService.updateProductByPrice(FIRST_ID, SECOND_PRICE);

    // Assert
    assertEquals(updatedProduct, actualProduct, "Should have the same values.");
  }

  private static Product createProduct(Long id, Double price) {
    Product product = new Product();
    product.setId(id);
    product.setName("Shampoo");
    product.setDescription("Body Care");
    product.setPrice(price);
    product.setInitialQuantity(100);
    product.setQuantity(76);
    product.setStartOfWeek(LocalDate.of(2024, 9, 1));
    product.setEndOfWeek(LocalDate.of(2024, 9,7));

    return product;
  }
}

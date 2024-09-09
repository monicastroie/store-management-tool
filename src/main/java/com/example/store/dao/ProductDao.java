package com.example.store.dao;

import com.example.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *  ProductDao extends JpaRepository to gain CRUD operations.
 * "Product" is the entity type and "Long" is the ID type.
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
}

package com.example.store.dao;

import com.example.store.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *  ProductDao extends CrudRepository to gain CRUD operations.
 * "Product" is the entity type and "Long" is the ID type.
 */
@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

}

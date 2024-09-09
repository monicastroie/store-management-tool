package com.example.store.dao;

import com.example.store.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 *  UserDao extends CrudRepository to gain CRUD operations.
 * "User" is the entity type and "Long" is the ID type.
 */
public interface UserDao extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);
}

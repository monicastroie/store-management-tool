package com.example.store.dao;

import com.example.store.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);
}

package com.example.store.services;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;

/**
 * Represents the service used for user operations
 */
public interface UserService {

  /**
   * Method for inserting a user in database
   * @param request
   */
  AuthenticationResponse register(RegisterRequest request);

  /**
   * Method used for authentication of a user
   * @param request
   */
  AuthenticationResponse login(AuthRequest request);
}

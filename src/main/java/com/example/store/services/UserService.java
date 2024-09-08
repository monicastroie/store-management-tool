package com.example.store.services;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;

public interface UserService {

  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse login(AuthRequest request);
}

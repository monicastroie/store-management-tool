package com.example.store.services;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;
import com.example.store.config.JwtService;
import com.example.store.dao.UserDao;
import com.example.store.entities.Role;
import com.example.store.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .fullName(request.getFullName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    userDao.save(user);
    return AuthenticationResponse.builder()
        .jwtToken(jwtService.generateJwtToken(user))
        .build();
  }

  public AuthenticationResponse login(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    var user = userDao.findByEmail(request.getEmail()).orElseThrow();
    return AuthenticationResponse.builder()
        .jwtToken(jwtService.generateJwtToken(user))
        .build();
  }
}

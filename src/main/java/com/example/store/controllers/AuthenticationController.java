package com.example.store.controllers;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;
import com.example.store.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(userService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(userService.login(request));
  }
}

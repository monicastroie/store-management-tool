package com.example.store.util;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;
import com.example.store.entities.Role;
import java.util.List;

public class UserUtil {

  public static RegisterRequest createRegisterRequest() {
    return RegisterRequest.builder()
        .fullName("John Snow")
        .email("john.snow@gmail.com")
        .password("1234")
        .role("ROLE_USER")
        .build();
  }

  public static AuthRequest createAuthRequest() {
    return AuthRequest.builder()
        .email("john.snow@gmail.com")
        .password("1234")
        .build();
  }

  public static AuthenticationResponse createAuthenticationResponse() {
    return AuthenticationResponse.builder()
        .jwtToken(getJwtToken())
        .build();
  }

  public static String getJwtToken() {
    return JwtUtil.generateToken("john.snow@gmail.com", List.of(Role.ROLE_USER.name()));
  }
}

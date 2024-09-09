package com.example.store.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;
import com.example.store.config.JwtService;
import com.example.store.dao.UserDao;
import com.example.store.entities.Role;
import com.example.store.entities.User;
import com.example.store.util.UserUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserDao userDao;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void testRegister() {
    // Given
    RegisterRequest request = UserUtil.createRegisterRequest();
    User user = createUser();
    AuthenticationResponse expectedResponse = UserUtil.createAuthenticationResponse();
    int index = expectedResponse.getJwtToken().indexOf(".");

    when(userDao.save(user)).thenReturn(user);
    when(jwtService.generateJwtToken(user)).thenReturn(UserUtil.getJwtToken());

    // Then
    AuthenticationResponse actualResponse = userService.register(request);

    // Assert
    assertEquals(expectedResponse.getJwtToken().substring(0, index),
        actualResponse.getJwtToken().substring(0, index),
        "Should have the same values.");
  }

  @Test
  void testLogin() {
    // Given
    AuthRequest request = UserUtil.createAuthRequest();
    User user = createUser();
    AuthenticationResponse expectedResponse = UserUtil.createAuthenticationResponse();
    int index = expectedResponse.getJwtToken().indexOf(".");

    when(userDao.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
    when(jwtService.generateJwtToken(user)).thenReturn(UserUtil.getJwtToken());

    // Then
    AuthenticationResponse actualResponse = userService.login(request);

    // Assert
    assertEquals(expectedResponse.getJwtToken().substring(0, index),
        actualResponse.getJwtToken().substring(0, index),
        "Should have the same values.");
  }

  private User createUser() {
    return User.builder()
        .fullName("John Snow")
        .email("john.snow@gmail.com")
        .password(passwordEncoder.encode("1234"))
        .role(Role.ROLE_USER)
        .build();
  }
}

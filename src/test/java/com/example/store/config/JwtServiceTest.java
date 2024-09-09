package com.example.store.config;

import java.lang.reflect.Field;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  private static final String SECRET_KEY = "7a88f285b330ac44dae66010385842e09b34d80a020f7e4ff51970732906cd57";
  private static final String USERNAME = "john.snow@gmail.com";

  @InjectMocks
  private JwtService jwtService;

  @Mock
  private UserDetails userDetails;

  @BeforeEach
  public void setUp() throws Exception {
    Field secretKeyField = JwtService.class.getDeclaredField("secretKey");
    secretKeyField.setAccessible(true);
    secretKeyField.set(jwtService, SECRET_KEY);
  }

  @Test
  void testGenerateJwtToken() {
    // Given
    when(userDetails.getUsername()).thenReturn(USERNAME);

    // Then
    String jwt = jwtService.generateJwtToken(userDetails);
    String extractedEmail = jwtService.extractEmail(jwt);

    // Assert
    assertNotNull(jwt);
    assertEquals(USERNAME, extractedEmail, "Should be the same values.");
  }

  @Test
  void testExtractEmail() {
    // Given
    when(userDetails.getUsername()).thenReturn(USERNAME);
    String jwt = jwtService.generateJwtToken(userDetails);

    // Then
    String email = jwtService.extractEmail(jwt);

    // Assert
    assertEquals(USERNAME, email, "Should be the same values.");
  }

  @Test
  void testIsJwtTokenValid() {
    // Given
    when(userDetails.getUsername()).thenReturn(USERNAME);
    String jwt = jwtService.generateJwtToken(userDetails);

    // Then
    boolean isValid = jwtService.isJwtTokenValid(jwt, userDetails);

    // Assert
    assertTrue(isValid, "Token should be valid.");
  }

  @Test
  void testExtractClaim() {
    // Given
    when(userDetails.getUsername()).thenReturn(USERNAME);
    String jwt = jwtService.generateJwtToken(userDetails);

    // Then
    Date expiration = jwtService.extractClaim(jwt, Claims::getExpiration);

    // Assert
    assertNotNull(expiration, "Expiration date should be present.");
  }
}

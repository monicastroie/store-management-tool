package com.example.store.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.store.auth.AuthRequest;
import com.example.store.auth.AuthenticationResponse;
import com.example.store.auth.RegisterRequest;
import com.example.store.services.UserService;
import com.example.store.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @Test
  void testRegister() throws Exception {
    // Given
    RegisterRequest request = UserUtil.createRegisterRequest();
    AuthenticationResponse response = UserUtil.createAuthenticationResponse();
    when(userService.register(request)).thenReturn(response);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    AuthenticationResponse actualRepsonse = objectMapper.readValue(result.getResponse().getContentAsString(),
        AuthenticationResponse.class);

    // Assert
    assertNotNull(actualRepsonse);
  }

  @Test
  void testLogin() throws Exception {
    // Given
    AuthRequest request = UserUtil.createAuthRequest();
    AuthenticationResponse response = UserUtil.createAuthenticationResponse();
    when(userService.login(request)).thenReturn(response);

    // Then
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    AuthenticationResponse actualRepsonse = objectMapper.readValue(result.getResponse().getContentAsString(),
        AuthenticationResponse.class);

    // Assert
    assertNotNull(actualRepsonse);
  }
}

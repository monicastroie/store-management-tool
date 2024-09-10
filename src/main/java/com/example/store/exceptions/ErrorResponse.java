package com.example.store.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Represents an error response.
 *  Each response has a status code and a message body.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private int statusCode;
  private String message;
}

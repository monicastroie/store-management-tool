package com.example.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  Represents a product in the store management application.
 *  Each product has an id, name, description, price, an initial quantity, and a value for the start
 *  and the end of the week
 * The value at the beginning of the week represents the date when the initial quantity value was
 * updated, while the value at the end of the week represents the time when the quantity of
 * each product must be filled.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  private Double price;

  private Integer initialQuantity;

  private Integer quantity;

  private LocalDate startOfWeek;

  private LocalDate endOfWeek;

  public Product(String name, String description, Double price, Integer initialQuantity,
      Integer quantity) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.initialQuantity = initialQuantity;
    this.quantity = quantity;
  }
}

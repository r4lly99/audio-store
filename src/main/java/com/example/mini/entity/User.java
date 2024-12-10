package com.example.mini.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private Long id;
}

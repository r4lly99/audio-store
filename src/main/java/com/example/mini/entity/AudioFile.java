package com.example.mini.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String filePath;

  @ManyToOne
  private User user;

  @ManyToOne
  private Phrase phrase;

  public AudioFile(String filePath, User user, Phrase phrase) {
    this.filePath = filePath;
    this.user = user;
    this.phrase = phrase;
  }
}

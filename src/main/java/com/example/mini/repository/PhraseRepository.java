package com.example.mini.repository;

import com.example.mini.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {

}

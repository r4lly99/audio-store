package com.example.mini.repository;

import com.example.mini.entity.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

  AudioFile findByUserIdAndPhraseId(Long userId, Long phraseId);
}
package com.example.mini.service.impl;

import com.example.mini.constant.AppConstants;
import com.example.mini.dto.AudioFileDto;
import com.example.mini.entity.AudioFile;
import com.example.mini.entity.Phrase;
import com.example.mini.entity.User;
import com.example.mini.repository.AudioFileRepository;
import com.example.mini.repository.PhraseRepository;
import com.example.mini.repository.UserRepository;
import com.example.mini.service.AudioService;
import com.example.mini.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AudioServiceImpl implements AudioService {

  private final AudioFileRepository audioFileRepository;
  private final UserRepository userRepository;
  private final PhraseRepository phraseRepository;

  @Override
  public AudioFileDto saveAudioFile(Long userId, Long phraseId, MultipartFile audioFile)
      throws IOException {
    // Retrieve User and Phrase entities
    final User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
    final Phrase phrase = phraseRepository.findById(phraseId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid phrase ID: " + phraseId));

    // Build file path
    String filePath = AppConstants.STORAGE_DIRECTORY + userId + "_" + phraseId
        + AppConstants.DEFAULT_FILE_EXTENSION;

    // Save file to disk
    FileUtil.saveFile(audioFile.getInputStream(), filePath);

    // Store metadata in database
    AudioFile audioFileEntity = new AudioFile(filePath, user, phrase);
    AudioFile savedAudioFile = audioFileRepository.save(audioFileEntity);

    // Convert to DTO
    AudioFileDto audioFileDto = new AudioFileDto();
    BeanUtils.copyProperties(savedAudioFile, audioFileDto);
    audioFileDto.setUserId(user.getId());
    audioFileDto.setPhraseId(phrase.getId());

    return audioFileDto;
  }

  @Override
  public byte[] retrieveAudioFile(Long userId, Long phraseId, String audioFormat)
      throws IOException {
    // Find the file metadata
    AudioFile audioFile = audioFileRepository.findByUserIdAndPhraseId(userId, phraseId);
    if (audioFile == null) {
      throw new IllegalArgumentException("No file found for the given user and phrase IDs.");
    }
    // Read file content
    return FileUtil.readFile(audioFile.getFilePath());
  }
}

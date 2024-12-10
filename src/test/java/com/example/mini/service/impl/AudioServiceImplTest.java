package com.example.mini.service.impl;

import com.example.mini.constant.AppConstants;
import com.example.mini.dto.AudioFileDto;
import com.example.mini.entity.AudioFile;
import com.example.mini.entity.Phrase;
import com.example.mini.entity.User;
import com.example.mini.repository.AudioFileRepository;
import com.example.mini.repository.PhraseRepository;
import com.example.mini.repository.UserRepository;
import com.example.mini.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AudioServiceImplTest {

  private AudioServiceImpl audioService;

  @Mock
  private AudioFileRepository audioFileRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PhraseRepository phraseRepository;

  @Mock
  private MultipartFile multipartFile;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    audioService = new AudioServiceImpl(audioFileRepository, userRepository, phraseRepository);
  }

  @Test
  void testSaveAudioFile() throws IOException {
    // Mock input data
    Long userId = 1L;
    Long phraseId = 2L;
    String filePath = AppConstants.STORAGE_DIRECTORY + userId + "_" + phraseId + AppConstants.DEFAULT_FILE_EXTENSION;

    User mockUser = new User(userId);
    Phrase mockPhrase = new Phrase(phraseId);

    InputStream mockInputStream = new ByteArrayInputStream("Mock content".getBytes());
    when(multipartFile.getInputStream()).thenReturn(mockInputStream);

    // Mock repositories
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(phraseRepository.findById(phraseId)).thenReturn(Optional.of(mockPhrase));
    when(audioFileRepository.save(any(AudioFile.class))).thenAnswer(invocation -> {
      AudioFile audioFile = invocation.getArgument(0);
      audioFile.setId(100L); // Simulate database ID assignment
      return audioFile;
    });

    // Mock FileUtil behavior using MockedStatic
    try (MockedStatic<FileUtil> mockedFileUtil = mockStatic(FileUtil.class)) {
      mockedFileUtil.when(() -> FileUtil.saveFile(any(InputStream.class), eq(filePath)))
          .thenAnswer(invocation -> null); // Simulate successful save

      // Call the method
      AudioFileDto result = audioService.saveAudioFile(userId, phraseId, multipartFile);

      // Verify the results
      assertEquals(filePath, result.getFilePath());
      assertEquals(userId, result.getUserId());
      assertEquals(phraseId, result.getPhraseId());

      // Verify interactions
      verify(userRepository, times(1)).findById(userId);
      verify(phraseRepository, times(1)).findById(phraseId);
      verify(audioFileRepository, times(1)).save(any(AudioFile.class));
      verify(multipartFile, times(1)).getInputStream();
      mockedFileUtil.verify(() -> FileUtil.saveFile(any(InputStream.class), eq(filePath)), times(1));
    }
  }
}


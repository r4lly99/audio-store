package com.example.mini.service;

import com.example.mini.dto.AudioFileDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface AudioService {

  AudioFileDto saveAudioFile(Long userId, Long phraseId, MultipartFile audioFile) throws IOException;

  byte[] retrieveAudioFile(Long userId, Long phraseId, String audioFormat) throws IOException;

}

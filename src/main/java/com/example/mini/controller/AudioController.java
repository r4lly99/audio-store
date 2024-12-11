package com.example.mini.controller;

import com.example.mini.constant.AppConstants;
import com.example.mini.dto.AudioFileDto;
import com.example.mini.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/audio")
@RequiredArgsConstructor
public class AudioController {

  private final AudioService audioService;

  @PostMapping("/user/{userId}/phrase/{phraseId}")
  public ResponseEntity<String> uploadAudioFile(
      @PathVariable Long userId,
      @PathVariable Long phraseId,
      @RequestParam("audio_file") MultipartFile audioFile) throws IOException {

    final AudioFileDto audioFileDto = audioService.saveAudioFile(userId, phraseId, audioFile);
    return ResponseEntity.ok("File uploaded successfully " +audioFileDto.getFilePath());
  }

  @GetMapping("/user/{userId}/phrase/{phraseId}/{audioFormat}")
  public ResponseEntity<byte[]> getAudioFile(
      @PathVariable Long userId,
      @PathVariable Long phraseId,
      @PathVariable String audioFormat) throws IOException {

    // Retrieve the file from service
    final byte[] fileContent = audioService.retrieveAudioFile(userId, phraseId, audioFormat);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment",
        AppConstants.DEFAULT_PREFIX_FORMAT + userId + "_" + phraseId + "." + audioFormat);

    return ResponseEntity.ok().headers(headers).body(fileContent);
  }

}

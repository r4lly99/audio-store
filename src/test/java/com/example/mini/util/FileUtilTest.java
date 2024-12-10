package com.example.mini.util;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

  private static final String TEST_FILE_PATH = "test_storage/test_file.wav";
  private static final String TEST_CONTENT = "This is a test.";

  @BeforeEach
  void setUp() throws IOException {
    File directory = new File("test_storage/");
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  @AfterEach
  void tearDown() {
    // Clean up after tests
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  void testSaveFile() throws IOException {
    InputStream inputStream = new ByteArrayInputStream(TEST_CONTENT.getBytes());

    // Call the method
    FileUtil.saveFile(inputStream, TEST_FILE_PATH);

    // Verify the file exists
    File file = new File(TEST_FILE_PATH);
    assertTrue(file.exists(), "File should exist after saving");

    // Verify the file content
    String content = Files.readString(file.toPath());
    assertEquals(TEST_CONTENT, content, "File content should match");
  }

  @Test
  void testReadFile() throws IOException {
    // Prepare a file
    File file = new File(TEST_FILE_PATH);
    Files.writeString(file.toPath(), TEST_CONTENT);

    // Call the method
    byte[] content = FileUtil.readFile(TEST_FILE_PATH);

    // Verify the content
    assertEquals(TEST_CONTENT, new String(content), "Read content should match the written content");
  }

  @Test
  void testDeleteFile() {
    // Prepare a file
    File file = new File(TEST_FILE_PATH);
    try {
      file.createNewFile();
    } catch (IOException e) {
      fail("Failed to create file for test");
    }

    // Verify the file exists
    assertTrue(file.exists(), "File should exist before deletion");

    // Call the method
    boolean isDeleted = FileUtil.deleteFile(TEST_FILE_PATH);

    // Verify the file is deleted
    assertTrue(isDeleted, "File should be successfully deleted");
    assertFalse(file.exists(), "File should no longer exist");
  }
}

package com.example.mini.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class FileUtil {

  public static void saveFile(InputStream inputStream, String destinationPath) throws IOException {
    try (InputStream stream = inputStream) {
      File destinationFile = new File(destinationPath);
      FileUtils.copyInputStreamToFile(stream, destinationFile);
    } catch (IOException e) {
      throw new IOException("Failed to save file to " + destinationPath, e);
    }
  }

  public static byte[] readFile(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IOException("File not found at " + filePath);
    }
    return FileUtils.readFileToByteArray(file);
  }

  public static boolean deleteFile(String filePath) {
    File file = new File(filePath);
    return file.exists() && file.delete();
  }

}

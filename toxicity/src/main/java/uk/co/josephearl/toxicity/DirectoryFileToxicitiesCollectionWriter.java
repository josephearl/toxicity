package uk.co.josephearl.toxicity;

import java.io.File;
import java.io.IOException;

public interface DirectoryFileToxicitiesCollectionWriter extends FileToxicitiesCollectionWriter {
  static void checkDirectoryIsWritable(File directory) throws IOException {
    if (directory.exists() && !directory.isDirectory()) {
      throw new IOException(String.format("File %s is a file, not a directory", directory));
    }
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException(String.format("Could not create directory %s", directory));
    }
  }

  File getEntryPoint();
}

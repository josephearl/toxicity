package uk.co.josephearl.toxicity;

import java.io.File;
import java.io.IOException;

public interface FileReporter<T> {
  void write(File file, FileToxicitiesCollection fileToxicitiesCollection) throws IOException;
}

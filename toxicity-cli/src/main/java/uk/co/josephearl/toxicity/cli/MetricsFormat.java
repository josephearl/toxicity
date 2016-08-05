package uk.co.josephearl.toxicity.cli;

import uk.co.josephearl.toxicity.FileMetricsCollectionReader;
import uk.co.josephearl.toxicity.metricsreader.checkstyle.CheckstyleFileMetricsCollectionReader;

import java.io.File;
import java.io.IOException;

public enum MetricsFormat {
  CHECKSTYLE("checkstyle") {
    @Override
    FileMetricsCollectionReader createReader(File file) throws IOException {
      return CheckstyleFileMetricsCollectionReader.create(file);
    }
  };

  private final String name;

  MetricsFormat(String name) {
    this.name = name;
  }

  abstract FileMetricsCollectionReader createReader(File file) throws IOException;

  @Override
  public String toString() {
    return name;
  }
}

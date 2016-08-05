package uk.co.josephearl.toxicity;

import java.io.File;

public final class FileMetrics {
  private final File file;
  private final Metrics metrics;

  private FileMetrics(File file, Metrics metrics) {
    this.file = file;
    this.metrics = metrics;
  }

  public static FileMetrics of(File file, Metrics metrics) {
    return new FileMetrics(file, metrics);
  }

  public File file() {
    return file;
  }

  public Metrics metricReadings() {
    return metrics;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileMetrics that = (FileMetrics) o;
    return file.equals(that.file) && metrics.equals(that.metrics);
  }

  @Override
  public int hashCode() {
    int result = file.hashCode();
    result = 31 * result + metrics.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "FileMetrics{File(\"" + file + "\"), " + metrics + '}';
  }
}

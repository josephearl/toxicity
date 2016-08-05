package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class FileMetricsTest {
  @Test
  public void file_isCorrect() {
    File file = new File("Test.checkstyle");
    Metrics metrics = Metrics.of(Collections.emptyList());

    FileMetrics fileMetrics = FileMetrics.of(file, metrics);

    assertThat(fileMetrics.file()).isEqualTo(file);
  }

  @Test
  public void metricReadings_isCorrect() {
    File file = new File("Test.checkstyle");
    Metrics metrics = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));

    FileMetrics fileMetrics = FileMetrics.of(file, metrics);

    assertThat(fileMetrics.metricReadings()).isEqualTo(metrics);
  }

  @Test
  public void equals_fileMetricReadings_areEqual() {
    File file1 = new File("Test.checkstyle");
    Metrics metrics1 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));
    File file2 = new File("Test.checkstyle");
    Metrics metrics2 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));

    FileMetrics fileMetrics1 = FileMetrics.of(file1, metrics1);
    FileMetrics fileMetrics2 = FileMetrics.of(file2, metrics2);

    assertThat(fileMetrics1).isEqualTo(fileMetrics2);
  }
}

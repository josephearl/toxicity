package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class FileMetricsCollectionTest {
  @Test
  public void size_isCorrect() {
    Metrics metrics = Metrics.of(Arrays.asList(
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));
    FileMetrics fileMetrics1 = FileMetrics.of(new File("File1.checkstyle"), metrics);
    FileMetrics fileMetrics2 = FileMetrics.of(new File("File2.checkstyle"), metrics);
    FileMetrics fileMetrics3 = FileMetrics.of(new File("File1.checkstyle"), metrics);

    FileMetricsCollection fileMetricsCollection = FileMetricsCollection.of(Arrays.asList(
        fileMetrics1, fileMetrics2, fileMetrics3
    ));

    assertThat(fileMetricsCollection).hasSize(3);
  }

  @Test
  public void equals_fileMetricReadingsCollection_areEqual() {
    File file1 = new File("Test.checkstyle");
    Metrics metrics1 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));
    FileMetrics fileMetrics1 = FileMetrics.of(file1, metrics1);
    File file2 = new File("Test.checkstyle");
    Metrics metrics2 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));
    FileMetrics fileMetrics2 = FileMetrics.of(file2, metrics2);

    FileMetricsCollection fileMetricsCollection1 =
        FileMetricsCollection.of(Arrays.asList(fileMetrics1, fileMetrics2));
    FileMetricsCollection fileMetricsCollection2 =
        FileMetricsCollection.of(Arrays.asList(fileMetrics2, fileMetrics2));

    assertThat(fileMetricsCollection1).isEqualTo(fileMetricsCollection2);
  }
}

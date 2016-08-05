package uk.co.josephearl.toxicity.metricsreader.checkstyle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.co.josephearl.toxicity.FileMetrics;
import uk.co.josephearl.toxicity.FileMetricsCollection;
import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Metrics;

import java.io.File;
import java.util.Arrays;

public class CheckstyleFileMetricsCollectionReaderTest {
  @Test
  public void read_dataIsCorrect() throws Exception {
    File inputFile = new File(getClass().getResource("checkstyle_file_metric_collection_file_reader_test.xml").getFile());
    File configurationFile = new File("..\\..\\tools\\hibernate\\src\\org\\hibernate\\cfg\\Configuration.java");
    FileMetrics configurationFileMetrics = FileMetrics.of(configurationFile, Metrics.of(Arrays.asList(
        Metric.of("FileLength", 1965),
        Metric.of("ClassDataAbstractionCoupling", 42),
        Metric.of("ClassFanOutComplexity", 95),
        Metric.of("MethodLength", 51),
        Metric.of("CyclomaticComplexity", 12),
        Metric.of("MethodLength", 77),
        Metric.of("CyclomaticComplexity", 16),
        Metric.of("MethodLength", 90),
        Metric.of("CyclomaticComplexity", 19),
        Metric.of("MethodLength", 120),
        Metric.of("MethodLength", 55),
        Metric.of("CyclomaticComplexity", 57),
        Metric.of("MethodLength", 239),
        Metric.of("AnonInnerLength", 36)
    )));

    try (CheckstyleFileMetricsCollectionReader checkstyleFileMetricsCollectionReader = CheckstyleFileMetricsCollectionReader.create(inputFile)) {
      FileMetricsCollection fileMetricsCollection = checkstyleFileMetricsCollectionReader.read();

      assertThat(fileMetricsCollection).hasSize(75);
      assertThat(fileMetricsCollection).contains(configurationFileMetrics);
    }
  }
}

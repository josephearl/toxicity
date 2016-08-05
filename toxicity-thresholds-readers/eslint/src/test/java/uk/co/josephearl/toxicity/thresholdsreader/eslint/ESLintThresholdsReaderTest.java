package uk.co.josephearl.toxicity.thresholdsreader.eslint;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class ESLintThresholdsReaderTest {
  @Test
  public void read_dataIsCorrect() throws Exception {
    Thresholds expected = Thresholds.of(new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("max-statements", 30)),
        Threshold.of(Metric.of("max-params", 6)),
        Threshold.of(Metric.of("complexity", 10)),
        Threshold.of(Metric.of("max-depth", 3)),
        Threshold.of(Metric.of("default-case", 1)),
        Threshold.of(Metric.of("no-fallthrough", 1))
    )));
    File inputFile = new File(getClass().getResource("eslint_thresholds_file_reader_test.json").getFile());

    try (ESLintThresholdsReader esLintThresholdsReader = ESLintThresholdsReader.create(inputFile)) {
      Thresholds thresholds = esLintThresholdsReader.read();

      assertThat(thresholds).hasSize(expected.size());
      assertThat(thresholds).containsAll(expected);
    }
  }
}

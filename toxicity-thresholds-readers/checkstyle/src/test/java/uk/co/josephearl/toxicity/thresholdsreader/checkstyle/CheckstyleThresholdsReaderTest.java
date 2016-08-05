package uk.co.josephearl.toxicity.thresholdsreader.checkstyle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class CheckstyleThresholdsReaderTest {
  @Test
  public void read_dataIsCorrect() throws Exception {
    Thresholds expected = Thresholds.of(new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("FileLength", 500)),
        Threshold.of(Metric.of("AnonInnerLength", 35)),
        Threshold.of(Metric.of("MethodLength", 30)),
        Threshold.of(Metric.of("ParameterNumber", 6)),
        Threshold.of(Metric.of("MissingSwitchDefault", 1)),
        Threshold.of(Metric.of("NestedIfDepth", 3)),
        Threshold.of(Metric.of("NestedIfDepth", 3)),
        Threshold.of(Metric.of("NestedTryDepth", 2)),
        Threshold.of(Metric.of("BooleanExpressionComplexity", 2)),
        Threshold.of(Metric.of("ClassDataAbstractionCoupling", 10)),
        Threshold.of(Metric.of("ClassFanOutComplexity", 30)),
        Threshold.of(Metric.of("CyclomaticComplexity", 10))
    )));
    File inputFile = new File(getClass().getResource("checkstyle_thresholds_file_reader_test.xml").getFile());

    try (CheckstyleThresholdsReader checkstyleThresholdsReader = CheckstyleThresholdsReader.create(inputFile)) {
      Thresholds thresholds = checkstyleThresholdsReader.read();

      assertThat(thresholds).hasSize(expected.size());
      assertThat(thresholds).containsAll(expected);
    }
  }
}

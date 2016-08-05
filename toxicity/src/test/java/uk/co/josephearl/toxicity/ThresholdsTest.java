package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.*;

public class ThresholdsTest {
  @Test
  public void size_isCorrect() {
    Set<Threshold> expected = new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("CyclomaticComplexity", 10)),
        Threshold.of(Metric.of("MethodLength", 30)),
        Threshold.of(Metric.of("FileLength", 500))
    ));

    Thresholds thresholds = Thresholds.of(expected);

    assertThat(thresholds).hasSize(3);
  }

  @Test
  public void get_isCorrect() {
    Threshold expected = Threshold.of(Metric.of("CyclomaticComplexity", 10));
    Thresholds thresholds = Thresholds.of(new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("MethodLength", 30)),
        expected,
        Threshold.of(Metric.of("FileLength", 500))
    )));

    Threshold threshold = thresholds.get("CyclomaticComplexity");

    assertThat(threshold).isEqualTo(expected);
  }

  @Test
  public void equals_metricThresholds_areEqual() {
    Set<Threshold> thresholdSet1 = new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("CyclomaticComplexity", 10)),
        Threshold.of(Metric.of("MethodLength", 30)),
        Threshold.of(Metric.of("FileLength", 500))
    ));
    Set<Threshold> thresholdSet2 = new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("MethodLength", 30)),
        Threshold.of(Metric.of("CyclomaticComplexity", 10)),
        Threshold.of(Metric.of("FileLength", 500))
    ));

    Thresholds thresholds1 = Thresholds.of(thresholdSet1);
    Thresholds thresholds2 = Thresholds.of(thresholdSet2);

    assertThat(thresholds1).isEqualTo(thresholds2);
  }
}

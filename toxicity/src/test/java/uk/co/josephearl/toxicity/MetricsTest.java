package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Arrays;

public class MetricsTest {
  @Test
  public void size_isCorrect() {
    Metrics metrics = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("MethodLength", 30),
        Metric.of("MethodLength", 45),
        Metric.of("FileLength", 600)
    ));

    assertThat(metrics).hasSize(5);
  }

  @Test
  public void equals_metricReadings_areEqual() {
    Metrics metrics1 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));
    Metrics metrics2 = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("FileLength", 600)
    ));

    assertThat(metrics1).isEqualTo(metrics2);
  }
}

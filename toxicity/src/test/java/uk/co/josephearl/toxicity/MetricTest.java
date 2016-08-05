package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MetricTest {
  @Test
  public void type_isCorrect() {
    String expected = "CyclomaticComplexity";
    Metric metric = Metric.of(expected, 1);

    String type = metric.type();

    assertThat(type).isEqualTo(expected);
  }

  @Test
  public void value_isCorrect() {
    int expected = 27;
    Metric metric = Metric.of("None", expected);

    int value = metric.value();

    assertThat(value).isEqualTo(expected);
  }

  @Test
  public void equals_metricReadings_areEqual() {
    Metric metric1 = Metric.of("None", 27);
    Metric metric2 = Metric.of("None", 27);

    assertThat(metric1).isEqualTo(metric2);
  }
}

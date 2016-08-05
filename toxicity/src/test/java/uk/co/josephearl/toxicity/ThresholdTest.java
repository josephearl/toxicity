package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ThresholdTest {
  @Test
  public void toxicity_readingBelowThreshold_isZero() {
    Metric metric = Metric.of("CyclomaticComplexity", 5);
    Threshold threshold = Threshold.of(Metric.of("CyclomaticComplexity", 10));

    Toxicity toxicity = threshold.toxicity(metric);

    assertThat(toxicity.isZero()).isTrue();
  }

  @Test
  public void toxicity_readingEqualToThreshold_iszero() {
    Metric metric = Metric.of("CyclomaticComplexity", 10);
    Threshold threshold = Threshold.of(Metric.of("CyclomaticComplexity", 10));

    Toxicity toxicity = threshold.toxicity(metric);

    assertThat(toxicity).isEqualTo(Toxicity.zero());
  }

  @Test
  public void toxicity_readingAboveThreshold_isScaledByThreshold() {
    Metric metric = Metric.of("CyclomaticComplexity", 15);
    Threshold threshold = Threshold.of(Metric.of("CyclomaticComplexity", 10));

    Toxicity toxicity = threshold.toxicity(metric);

    assertThat(toxicity).isEqualTo(Toxicity.of(1.5d));
  }

  @Test(expected = IllegalArgumentException.class)
  public void toxicity_differentMetricTypes_throwsException() {
    Metric metric = Metric.of("CyclomaticComplexity", 15);
    Threshold threshold = Threshold.of(Metric.of("MethodLength", 10));

    threshold.toxicity(metric);
  }

  @Test
  public void type_isCorrect() throws Exception {
    String expected = "MethodLength";
    Threshold threshold = Threshold.of(Metric.of(expected, 1));

    String type = threshold.type();

    assertThat(type).isEqualTo(expected);
  }

  @Test
  public void value_isCorrect() throws Exception {
    int expected = 20;
    Threshold threshold = Threshold.of(Metric.of("CyclomaticComplexity", expected));

    int value = threshold.value();

    assertThat(value).isEqualTo(expected);
  }

  @Test
  public void equals_thresholdsWithSameMetricTypeButDifferentValue_areEqual() throws Exception {
    Threshold threshold1 = Threshold.of(Metric.of("CyclomaticComplexity", 20));
    Threshold threshold2 = Threshold.of(Metric.of("CyclomaticComplexity", 10));

    assertThat(threshold1).isEqualTo(threshold2);
  }
}
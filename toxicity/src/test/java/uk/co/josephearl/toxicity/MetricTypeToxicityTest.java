package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MetricTypeToxicityTest {
  @Test
  public void type_isCorrect() throws Exception {
    String expected = "FileLength";
    MetricTypeToxicity metricTypeToxicity = MetricTypeToxicity.of(expected, Toxicity.of(2.0));

    String type = metricTypeToxicity.type();

    assertThat(type).isEqualTo(expected);
  }

  @Test
  public void toxicity_isCorrect() throws Exception {
    Toxicity expected = Toxicity.of(3.4);
    MetricTypeToxicity metricTypeToxicity = MetricTypeToxicity.of("CyclomaticComplexity", expected);

    Toxicity toxicity = metricTypeToxicity.toxicity();

    assertThat(toxicity).isEqualTo(expected);
  }
}
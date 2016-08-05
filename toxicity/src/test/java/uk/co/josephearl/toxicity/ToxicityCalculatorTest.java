package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ToxicityCalculatorTest {
  @Test
  public void calculate_metricToxicities_isCorrect() {
    Toxicities expected = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("MethodLength", Toxicity.of(1.5d));
      put("FileLength", Toxicity.of(1.2d));
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("AnonInnerLength", Toxicity.zero());
    }});
    Metrics metrics = Metrics.of(Arrays.asList(
        Metric.of("CyclomaticComplexity", 11),
        Metric.of("MethodLength", 15),
        Metric.of("MethodLength", 30),
        Metric.of("MethodLength", 45),
        Metric.of("FileLength", 600)
    ));
    Thresholds thresholds = Thresholds.of(new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("CyclomaticComplexity", 10)),
        Threshold.of(Metric.of("MethodLength", 30)),
        Threshold.of(Metric.of("FileLength", 500)),
        Threshold.of(Metric.of("AnonInnerLength", 35))
    )));
    ToxicityCalculator calculator = ToxicityCalculator.create(thresholds);

    Toxicities toxicities = calculator.calculate(metrics);

    assertThat(toxicities).isEqualTo(expected);
  }
}

package uk.co.josephearl.toxicity.thresholds.eslint;

import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;

import java.util.Arrays;
import java.util.HashSet;

public final class ESLintThresholds {
  private ESLintThresholds() {
  }

  public static Thresholds eslintThresholds() {
    return Thresholds.of(new HashSet<>(Arrays.asList(
        Threshold.of(Metric.of("max-statements", 30)),
        Threshold.of(Metric.of("max-params", 6)),
        Threshold.of(Metric.of("complexity", 10)),
        Threshold.of(Metric.of("max-depth", 3)),
        Threshold.of(Metric.of("default-case", 1)),
        Threshold.of(Metric.of("no-fallthrough", 1))
    )));
  }
}

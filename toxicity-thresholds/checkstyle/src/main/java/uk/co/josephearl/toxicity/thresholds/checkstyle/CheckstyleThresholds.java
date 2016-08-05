package uk.co.josephearl.toxicity.thresholds.checkstyle;

import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;

import java.util.Arrays;
import java.util.HashSet;

public final class CheckstyleThresholds {
  private CheckstyleThresholds() {
  }

  public static Thresholds checkstyleThresholds() {
    return Thresholds.of(new HashSet<>(Arrays.asList(
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
  }
}

package uk.co.josephearl.toxicity;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ToxicityCalculator {
  private final Thresholds thresholds;

  private ToxicityCalculator(Thresholds thresholds) {
    this.thresholds = thresholds;
  }

  public static ToxicityCalculator create(Thresholds thresholds) {
    return new ToxicityCalculator(thresholds);
  }

  public Toxicities calculate(Metrics metrics) {
    return metrics.stream()
        .filter(r -> thresholds.containsMetricType(r.type()))
        .map(this::calculateMetricTypeToxicity)
        .collect(Collectors.collectingAndThen(toMap(), this::createToxicities));
  }

  private MetricTypeToxicity calculateMetricTypeToxicity(Metric metric) {
    return MetricTypeToxicity.of(metric.type(), calculateToxicity(metric));
  }

  private Toxicity calculateToxicity(Metric metric) {
    return thresholds.get(metric.type()).toxicity(metric);
  }

  private Collector<MetricTypeToxicity, ?, Map<String, Toxicity>> toMap() {
    return Collectors.toMap(MetricTypeToxicity::type, MetricTypeToxicity::toxicity, this::sumToxicityForMetric);
  }

  private Toxicity sumToxicityForMetric(Toxicity toxicity, Toxicity toxicity2) {
    return Toxicity.of(toxicity.value() + toxicity2.value());
  }

  private Toxicities createToxicities(Map<String, Toxicity> toxicities) {
    thresholds.forEach(threshold -> toxicities.putIfAbsent(threshold.type(), Toxicity.zero()));
    return Toxicities.of(toxicities);
  }
}

package uk.co.josephearl.toxicity;

public final class Threshold {
  private final Metric thresholdMetric;

  private Threshold(Metric thresholdMetric) {
    this.thresholdMetric = thresholdMetric;
  }

  public static Threshold of(Metric metric) {
    return new Threshold(metric);
  }

  public Toxicity toxicity(Metric metric) {
    return metric.compareTo(this.thresholdMetric) > 0 ?
        Toxicity.of(metric.value() / (double) this.thresholdMetric.value()) :
        Toxicity.zero();
  }

  public String type() {
    return thresholdMetric.type();
  }

  public int value() {
    return thresholdMetric.value();
  }

  public boolean equalsMetric(Metric metric) {
    return thresholdMetric.equals(metric);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Threshold that = (Threshold) o;
    return thresholdMetric.type().equals(that.thresholdMetric.type());
  }

  @Override
  public int hashCode() {
    return thresholdMetric.type().hashCode();
  }

  @Override
  public String toString() {
    return "Threshold(" + thresholdMetric + ')';
  }
}

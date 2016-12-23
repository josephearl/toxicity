package uk.co.josephearl.toxicity;

public final class Threshold {
  private final Metric metric;

  private Threshold(Metric metric) {
    this.metric = metric;
  }

  public static Threshold of(Metric metric) {
    return new Threshold(metric);
  }

  public Toxicity toxicity(Metric metric) {
    return metric.compareTo(this.metric) > 0 ?
        Toxicity.of(metric.value() / (double) this.metric.value()) :
        Toxicity.zero();
  }

  public String type() {
    return metric.type();
  }

  public int value() {
    return metric.value();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Threshold that = (Threshold) o;
    return metric.type().equals(that.metric.type());
  }

  @Override
  public int hashCode() {
    return metric.type().hashCode();
  }

  @Override
  public String toString() {
    return "Threshold(" + metric + ')';
  }
}

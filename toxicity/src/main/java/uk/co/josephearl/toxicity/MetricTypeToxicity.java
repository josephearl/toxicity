package uk.co.josephearl.toxicity;

final class MetricTypeToxicity {
  private final String type;
  private final Toxicity toxicity;

  private MetricTypeToxicity(String type, Toxicity toxicity) {
    this.type = type;
    this.toxicity = toxicity;
  }

  static MetricTypeToxicity of(String type, Toxicity toxicity) {
    return new MetricTypeToxicity(type, toxicity);
  }

  String type() {
    return type;
  }

  Toxicity toxicity() {
    return toxicity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MetricTypeToxicity that = (MetricTypeToxicity) o;
    return type.equals(that.type) && toxicity.equals(that.toxicity);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + toxicity.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return type + "(" + toxicity + ')';
  }
}

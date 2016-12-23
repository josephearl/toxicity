package uk.co.josephearl.toxicity;

public final class Toxicity implements Comparable<Toxicity> {
  private static final Toxicity ZERO = new Toxicity(0d);

  private final double value;

  private Toxicity(double value) {
    this.value = value;
  }

  public static Toxicity of(double value) {
    if (value < 0.0d) {
      throw new IllegalArgumentException(String.format("Toxicity value must be >= 0, was %f", value));
    }
    if (Double.compare(0d, value) == 0) {
      return ZERO;
    }
    return new Toxicity(value);
  }

  public static Toxicity zero() {
    return ZERO;
  }

  public double value() {
    return value;
  }

  public boolean isZero() {
    return this == ZERO;
  }

  public Toxicity add(Toxicity toxicity) {
    return Toxicity.of(value + toxicity.value);
  }

  @Override
  public int compareTo(Toxicity toxicity) {
    return Double.compare(this.value, toxicity.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Toxicity that = (Toxicity) o;
    return Double.compare(that.value, this.value) == 0;
  }

  @Override
  public int hashCode() {
    long temp = Double.doubleToLongBits(value);
    return (int) (temp ^ (temp >>> 32));
  }

  @Override
  public String toString() {
    return "Toxicity(" + value + ')';
  }
}

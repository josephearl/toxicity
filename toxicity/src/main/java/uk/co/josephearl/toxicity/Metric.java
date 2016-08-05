package uk.co.josephearl.toxicity;

// TODO: rename to type
public final class Metric implements Comparable<Metric> {
  private final String type;
  private final int value;

  private Metric(String type, int value) {
    this.type = type;
    this.value = value;
  }

  public static Metric of(String type, int value) {
    return new Metric(type, value);
  }

  public String type() {
    return type;
  }

  public int value() {
    return value;
  }

  @Override
  public int compareTo(Metric o) {
    if (!type.equals(o.type)) {
      throw new IllegalArgumentException(String.format("Metric types are different, cannot compare type '%s' to type '%s'", type, o.type));
    }
    return Integer.compare(value, o.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metric that = (Metric) o;
    return value == that.value && type.equals(that.type);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + value;
    return result;
  }

  @Override
  public String toString() {
    return type + "(" + value + ')';
  }
}

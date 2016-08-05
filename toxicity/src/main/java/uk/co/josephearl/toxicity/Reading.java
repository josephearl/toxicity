package uk.co.josephearl.toxicity;

public final class Reading implements Comparable<Reading> {
  private final int value;

  private Reading(int value) {
    this.value = value;
  }

  public static Reading of(final int reading) {
    return new Reading(reading);
  }

  public int value() {
    return value;
  }

  @Override
  public int compareTo(Reading reading) {
    return Integer.compare(value, reading.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reading that = (Reading) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public String toString() {
    return "Reading(" + value + ')';
  }
}

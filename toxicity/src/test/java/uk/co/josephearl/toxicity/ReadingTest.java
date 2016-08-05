package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ReadingTest {
  @Test
  public void value_isCorrect() {
    Reading reading = Reading.of(20);

    int value = reading.value();

    assertThat(value).isEqualTo(20);
  }

  @Test
  public void equals_readings_areEqual() {
    Reading reading1 = Reading.of(18);
    Reading reading2 = Reading.of(18);

    assertThat(reading1).isEqualTo(reading2);
  }

  @Test
  public void compareTo_equalToOther_isZero() {
    Reading reading = Reading.of(20);
    Reading other = Reading.of(20);

    int value = reading.compareTo(other);

    assertThat(value).isZero();
  }

  @Test
  public void compareTo_lessThanOther_isNegative() {
    Reading reading = Reading.of(20);
    Reading other = Reading.of(30);

    int value = reading.compareTo(other);

    assertThat(value).isLessThanOrEqualTo(-1);
  }

  @Test
  public void compareTo_greaterThanOther_isPositive() {
    Reading reading = Reading.of(20);
    Reading other = Reading.of(10);

    int value = reading.compareTo(other);

    assertThat(value).isGreaterThanOrEqualTo(1);
  }
}

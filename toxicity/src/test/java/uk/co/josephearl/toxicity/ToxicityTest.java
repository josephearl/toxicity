package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ToxicityTest {
  @Test
  public void of_zero_returnsStaticInstance() {
    Toxicity toxicity = Toxicity.of(0d);

    assertThat(toxicity == Toxicity.zero()).isTrue();
  }

  @Test
  public void of_zero_returnsSameInstance() {
    Toxicity toxicity1 = Toxicity.of(0d);
    Toxicity toxicity2 = Toxicity.of(0.0d);

    assertThat(toxicity1 == toxicity2).isTrue();
  }

  @Test
  public void value_isCorrect() {
    Toxicity toxicity = Toxicity.of(1.72d);

    double doubleToxicity = toxicity.value();

    assertThat(doubleToxicity).isEqualTo(1.72d);
  }

  @Test
  public void isZero_ofZero_isCorrect() {
    Toxicity toxicity = Toxicity.of(0.0d);

    boolean isZero = toxicity.isZero();

    assertThat(isZero).isTrue();
  }

  @Test
  public void isZero_ofNonZero_isCorrect() {
    Toxicity toxicity = Toxicity.of(0.1d);

    boolean isZero = toxicity.isZero();

    assertThat(isZero).isFalse();
  }

  @Test
  public void equals_toxicities_areEqual() {
    Toxicity toxicity1 = Toxicity.of(0.03d);
    Toxicity toxicity2 = Toxicity.of(0.03d);

    assertThat(toxicity1).isEqualTo(toxicity2);
  }

  @Test
  public void compareTo_equalToOther_isZero() {
    Toxicity toxicity = Toxicity.of(20);
    Toxicity other = Toxicity.of(20);

    int value = toxicity.compareTo(other);

    assertThat(value).isZero();
  }

  @Test
  public void compareTo_lessThanOther_isOne() {
    Toxicity toxicity = Toxicity.of(20);
    Toxicity other = Toxicity.of(30);

    int value = toxicity.compareTo(other);

    assertThat(value).isEqualTo(-1);
  }

  @Test
  public void compareTo_greaterThanOther_isOne() {
    Toxicity toxicity = Toxicity.of(20);
    Toxicity other = Toxicity.of(10);

    int value = toxicity.compareTo(other);

    assertThat(value).isEqualTo(1);
  }
}

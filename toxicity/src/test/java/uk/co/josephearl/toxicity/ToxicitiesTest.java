package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.HashMap;

public class ToxicitiesTest {
  @Test
  public void of_sortsByToxicity() {
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});

    assertThat(toxicities.firstKey()).isEqualTo("MethodLength");
    assertThat(toxicities.lastKey()).isEqualTo("CyclomaticComplexity");
  }

  @Test
  public void size_isCorrect() {
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
      put("AnonInnerLength", Toxicity.zero());
      put("MissingSwitchDefault", Toxicity.zero());
    }});

    assertThat(toxicities).hasSize(5);
  }

  @Test
  public void totalToxicity_sumsToxicities() {
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});

    assertThat(toxicities.totalToxicity()).isEqualTo(Toxicity.of(4.8d));
  }

  @Test
  public void equals_metricToxicities_areEqual() {
    Toxicities toxicities1 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});
    Toxicities toxicities2 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
      put("CyclomaticComplexity", Toxicity.of(1.1d));
    }});

    assertThat(toxicities1).isEqualTo(toxicities2);
  }

  @Test
  public void get_otherMetric_returnsNull() {
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});

    assertThat(toxicities.get("Other")).isNull();
  }
}

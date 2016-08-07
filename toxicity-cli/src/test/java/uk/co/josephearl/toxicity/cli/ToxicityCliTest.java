package uk.co.josephearl.toxicity.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import org.junit.Test;

import java.io.File;

public class ToxicityCliTest {
  @Test
  public void main_writesToxicitiesFile() throws Exception {
    File toxicitiesOutputFile = File.createTempFile("toxicities", ".csv");
    assertThat(toxicitiesOutputFile.delete()).isTrue();
    try {
      File expectedToxicitiesFile = new File(getClass().getResource("toxicity_cli_test_toxicities.csv").getFile());
      File thresholdsFile = new File(getClass().getResource("toxicity_cli_test_thresholds.xml").getFile());
      File metricsFile = new File(getClass().getResource("toxicity_cli_test_metrics.xml").getFile());
      String[] args = new String[]{thresholdsFile.getPath(), metricsFile.getPath(), toxicitiesOutputFile.getPath()};

      ToxicityCli.main(args);

      assertThat(contentOf(toxicitiesOutputFile)).isEqualTo(contentOf(expectedToxicitiesFile));
    } finally {
      assertThat(toxicitiesOutputFile.delete()).isTrue();
    }
  }

  @Test
  public void name_ofInstance_isCorrect() {
    String expected = "my-toxicity-cli";
    ToxicityCli toxicityCli = ToxicityCli.create(expected);

    String name = toxicityCli.name();

    assertThat(name).isEqualTo(expected);
  }
}

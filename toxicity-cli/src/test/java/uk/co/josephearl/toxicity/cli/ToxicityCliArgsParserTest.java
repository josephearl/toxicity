package uk.co.josephearl.toxicity.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.File;

public class ToxicityCliArgsParserTest {
  @Test
  public void parse_positionalArguments_isCorrect() throws Exception {
    String[] args = new String[]{"thresholds.xml", "metrics.xml", "toxicities.csv"};

    ToxicityCliArgs toxicityCliArgs = ToxicityCliArgsParser.named("test-cli").parse(args);

    assertThat(toxicityCliArgs.thresholdsFile()).isEqualTo(new File("thresholds.xml"));
    assertThat(toxicityCliArgs.thresholdsFileFormat()).isEqualTo(ThresholdsFormat.CHECKSTYLE);
    assertThat(toxicityCliArgs.metricsFile()).isEqualTo(new File("metrics.xml"));
    assertThat(toxicityCliArgs.metricsFileFormat()).isEqualTo(MetricsFormat.CHECKSTYLE);
    assertThat(toxicityCliArgs.toxicitiesFile()).isEqualTo(new File("toxicities.csv"));
    assertThat(toxicityCliArgs.toxicitiesFileFormat()).isEqualTo(ToxicitiesFormat.CSV);
  }

  @Test
  public void parse_allArguments_isCorrect() throws Exception {
    String[] args = new String[]{
        "--thresholds-format", "checkstyle", "thresholds.xml",
        "--metrics-format", "checkstyle", "metrics.xml",
        "--toxicities-format", "csv", "toxicities.csv"
    };

    ToxicityCliArgs toxicityCliArgs = ToxicityCliArgsParser.named("test-cli").parse(args);

    assertThat(toxicityCliArgs.thresholdsFile()).isEqualTo(new File("thresholds.xml"));
    assertThat(toxicityCliArgs.thresholdsFileFormat()).isEqualTo(ThresholdsFormat.CHECKSTYLE);
    assertThat(toxicityCliArgs.metricsFile()).isEqualTo(new File("metrics.xml"));
    assertThat(toxicityCliArgs.metricsFileFormat()).isEqualTo(MetricsFormat.CHECKSTYLE);
    assertThat(toxicityCliArgs.toxicitiesFile()).isEqualTo(new File("toxicities.csv"));
    assertThat(toxicityCliArgs.toxicitiesFileFormat()).isEqualTo(ToxicitiesFormat.CSV);
  }

  @Test(expected = ToxicityCliArgsParseException.class)
  public void parse_tooFewArguments_throwsException() throws Exception {
    String[] args = new String[]{"thresholds.xml", "metrics.xml"};

    ToxicityCliArgsParser.named("test-cli").parse(args);
  }

  @Test(expected = ToxicityCliArgsParseException.class)
  public void parse_invalidThresholdsFormat_throwsException() throws Exception {
    String[] args = new String[]{"thresholds.xml", "metrics.xml", "toxicities.csv", "--thresholds-format", "foobar"};

    ToxicityCliArgsParser.named("test-cli").parse(args);
  }

  @Test(expected = ToxicityCliArgsParseException.class)
  public void parse_invalidMetricsFormat_throwsException() throws Exception {
    String[] args = new String[]{"thresholds.xml", "metrics.xml", "toxicities.csv", "--metrics-format", "foobar"};

    ToxicityCliArgsParser.named("test-cli").parse(args);
  }

  @Test(expected = ToxicityCliArgsParseException.class)
  public void parse_invalidToxicitiesFormat_throwsException() throws Exception {
    String[] args = new String[]{"thresholds.xml", "metrics.xml", "toxicities.csv", "--toxicities-format", "foobar"};

    ToxicityCliArgsParser.named("test-cli").parse(args);
  }
}

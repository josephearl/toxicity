package uk.co.josephearl.toxicity.cli;

import uk.co.josephearl.toxicity.*;

import java.io.File;
import java.io.IOException;

public final class ToxicityCli {
  private static final String CLI_NAME = "toxicity-cli";
  private static final ToxicityCli INSTANCE = new ToxicityCli(CLI_NAME);
  private final String name;
  private final ToxicityCliArgsParser argsParser;

  private ToxicityCli(String name) {
    this.name = name;
    argsParser = ToxicityCliArgsParser.named(name);
  }

  public static ToxicityCli create(String name) {
    return new ToxicityCli(name);
  }

  public static void main(String[] args) throws Exception {
    ToxicityCli.INSTANCE.execute(args);
  }

  public String name() {
    return name;
  }

  public void execute(String[] args) throws ToxicityCliArgsParseException, IOException {
    execute(argsParser.parse(args));
  }

  public void execute(ToxicityCliArgs args) throws IOException {
    Thresholds thresholds = readThresholds(args.thresholdsFile(), args.thresholdsFileFormat());
    FileMetricsCollection metrics = readMetrics(args.metricsFile(), args.metricsFileFormat());

    ToxicityCalculator toxicityCalculator = ToxicityCalculator.create(thresholds);
    FileToxicitiesCollection toxicities = metrics.toxicities(toxicityCalculator);

    writeToxicities(args.toxicitiesFile(), args.toxicitiesFileFormat(), toxicities);
  }

  private Thresholds readThresholds(File thresholdsFile, ThresholdsFormat thresholdsFormat) throws IOException {
    try (ThresholdsReader reader = thresholdsFormat.createReader(thresholdsFile)) {
      return reader.read();
    } catch (IOException e) {
      System.err.println(String.format("%s: error: could not createReader from thresholds file: '%s'", "toxicity-cli", thresholdsFile));
      throw e;
    }
  }

  private FileMetricsCollection readMetrics(File metricsFile, MetricsFormat metricsFormat) throws IOException {
    try (FileMetricsCollectionReader reader = metricsFormat.createReader(metricsFile)) {
      return reader.read();
    } catch (IOException e) {
      System.err.println(String.format("%s: error: could not createReader from metrics file: '%s'", "toxicity-cli", metricsFile));
      throw e;
    }
  }

  private void writeToxicities(File toxicitiesFile, ToxicitiesFormat toxicitiesFormat, FileToxicitiesCollection fileToxicitiesCollection) throws IOException {
    try (FileToxicitiesCollectionWriter reporter = toxicitiesFormat.createWriter(toxicitiesFile)) {
      reporter.write(fileToxicitiesCollection);
    } catch (IOException e) {
      System.err.println(String.format("%s: error: could not write to toxicities file: '%s'", "toxicity-cli", toxicitiesFile));
      throw e;
    }
  }
}

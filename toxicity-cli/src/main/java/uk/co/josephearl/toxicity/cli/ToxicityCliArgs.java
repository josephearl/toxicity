package uk.co.josephearl.toxicity.cli;

import java.io.File;

public final class ToxicityCliArgs {
  private final File thresholdsFile;
  private final ThresholdsFormat thresholdsFormat;
  private final File metricsFile;
  private final MetricsFormat metricsFormat;
  private final File toxicitiesFile;
  private final ToxicitiesFormat toxicitiesFormat;

  ToxicityCliArgs(File thresholdsFile, ThresholdsFormat thresholdsFormat, File metricsFile, MetricsFormat metricsFormat, File toxicitiesFile, ToxicitiesFormat toxicitiesFormat) {
    this.thresholdsFile = thresholdsFile;
    this.thresholdsFormat = thresholdsFormat;
    this.metricsFile = metricsFile;
    this.metricsFormat = metricsFormat;
    this.toxicitiesFile = toxicitiesFile;
    this.toxicitiesFormat = toxicitiesFormat;
  }

  private ToxicityCliArgs(Builder builder) {
    this.thresholdsFile = builder.thresholdsFile;
    this.thresholdsFormat = builder.thresholdsFormat;
    this.metricsFile = builder.metricsFile;
    this.metricsFormat = builder.metricsFormat;
    this.toxicitiesFile = builder.toxicitiesFile;
    this.toxicitiesFormat = builder.toxicitiesFormat;
  }

  public static Builder builder() {
    return new Builder();
  }

  public File thresholdsFile() {
    return thresholdsFile;
  }

  public ThresholdsFormat thresholdsFileFormat() {
    return thresholdsFormat;
  }

  public File metricsFile() {
    return metricsFile;
  }

  public MetricsFormat metricsFileFormat() {
    return metricsFormat;
  }

  public File toxicitiesFile() {
    return toxicitiesFile;
  }

  public ToxicitiesFormat toxicitiesFileFormat() {
    return toxicitiesFormat;
  }

  public static final class Builder {
    private File thresholdsFile;
    private ThresholdsFormat thresholdsFormat;
    private File metricsFile;
    private MetricsFormat metricsFormat;
    private File toxicitiesFile;
    private ToxicitiesFormat toxicitiesFormat;

    private Builder() {
    }

    public ToxicityCliArgs build() {
      ensureNonNull(thresholdsFile, "thresholdsFile");
      ensureNonNull(thresholdsFormat, "thresholdsFormat");
      ensureNonNull(metricsFile, "metricsFile");
      ensureNonNull(metricsFormat, "metricsFormat");
      ensureNonNull(toxicitiesFile, "toxicitiesFile");
      ensureNonNull(toxicitiesFormat, "toxicitiesFormat");

      return new ToxicityCliArgs(this);
    }

    public Builder thresholdsFile(File thresholdsFile) {
      this.thresholdsFile = thresholdsFile;
      return this;
    }

    public Builder thresholdsFileFormat(ThresholdsFormat thresholdsFormat) {
      this.thresholdsFormat = thresholdsFormat;
      return this;
    }

    public Builder metricsFile(File metricsFile) {
      this.metricsFile = metricsFile;
      return this;
    }

    public Builder metricsFileFormat(MetricsFormat metricsFormat) {
      this.metricsFormat = metricsFormat;
      return this;
    }

    public Builder toxicitiesFile(File toxicitiesFile) {
      this.toxicitiesFile = toxicitiesFile;
      return this;
    }

    public Builder toxicitiesFileFormat(ToxicitiesFormat toxicitiesFormat) {
      this.toxicitiesFormat = toxicitiesFormat;
      return this;
    }

    private void ensureNonNull(Object value, String fieldName) {
      if (value == null) {
        throw new IllegalStateException(String.format("%s == null", fieldName));
      }
    }
  }
}

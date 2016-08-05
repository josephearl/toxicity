package uk.co.josephearl.toxicity;

import java.io.File;

public final class FileToxicities {
  private final File file;
  private final Toxicities toxicities;

  private FileToxicities(File file, Toxicities toxicities) {
    this.file = file;
    this.toxicities = toxicities;
  }

  public static FileToxicities of(File file, Toxicities toxicities) {
    return new FileToxicities(file, toxicities);
  }

  public File file() {
    return file;
  }

  public Toxicities metricToxicities() {
    return toxicities;
  }

  public Toxicity totalToxicity() {
    return toxicities.totalToxicity();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileToxicities that = (FileToxicities) o;
    return file.equals(that.file) && toxicities.equals(that.toxicities);
  }

  @Override
  public int hashCode() {
    int result = file.hashCode();
    result = 31 * result + toxicities.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "FileToxicities{File(\"" + file + "\"), " + toxicities + '}';
  }
}

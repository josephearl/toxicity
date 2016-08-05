package uk.co.josephearl.toxicity.cli;

import uk.co.josephearl.toxicity.ThresholdsReader;
import uk.co.josephearl.toxicity.thresholdsreader.checkstyle.CheckstyleThresholdsReader;
import uk.co.josephearl.toxicity.thresholdsreader.eslint.ESLintThresholdsReader;

import java.io.File;
import java.io.IOException;

public enum ThresholdsFormat {
  CHECKSTYLE("checkstyle") {
    @Override
    ThresholdsReader createReader(File file) throws IOException {
      return CheckstyleThresholdsReader.create(file);
    }
  },

  ESLINT("eslint") {
    @Override
    ThresholdsReader createReader(File file) throws IOException {
      return ESLintThresholdsReader.create(file);
    }
  };

  private final String name;

  ThresholdsFormat(String name) {
    this.name = name;
  }


  abstract ThresholdsReader createReader(File file) throws IOException;

  @Override
  public String toString() {
    return name;
  }
}

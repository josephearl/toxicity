package uk.co.josephearl.toxicity.cli;

import uk.co.josephearl.toxicity.FileToxicitiesCollectionWriter;
import uk.co.josephearl.toxicity.toxicitieswriter.csv.CsvFileToxicitiesCollectionWriter;
import uk.co.josephearl.toxicity.toxicitieswriter.html.HtmlFileToxicitiesCollectionWriter;

import java.io.File;
import java.io.IOException;

public enum ToxicitiesFormat {
  CSV("csv") {
    @Override
    FileToxicitiesCollectionWriter createWriter(File file) throws IOException {
      return CsvFileToxicitiesCollectionWriter.create(file);
    }
  },

  HTML("html") {
    @Override
    FileToxicitiesCollectionWriter createWriter(File file) throws IOException {
      return HtmlFileToxicitiesCollectionWriter.create(file);
    }
  };

  private final String name;

  ToxicitiesFormat(String name) {
    this.name = name;
  }

  abstract FileToxicitiesCollectionWriter createWriter(File file) throws IOException;

  @Override
  public String toString() {
    return name;
  }
}

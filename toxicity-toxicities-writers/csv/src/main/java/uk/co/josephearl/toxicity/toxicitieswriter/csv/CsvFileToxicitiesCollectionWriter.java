package uk.co.josephearl.toxicity.toxicitieswriter.csv;

import uk.co.josephearl.toxicity.FileToxicities;
import uk.co.josephearl.toxicity.FileToxicitiesCollection;
import uk.co.josephearl.toxicity.FileToxicitiesCollectionWriter;
import uk.co.josephearl.toxicity.Toxicity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CsvFileToxicitiesCollectionWriter implements FileToxicitiesCollectionWriter {
  private static final String FILE_COLUMN_NAME = "File";
  private static final String TOXICITY_COLUMN_NAME = "Toxicity";
  private final Writer writer;

  private CsvFileToxicitiesCollectionWriter(Writer writer) {
    this.writer = writer;
  }

  public static CsvFileToxicitiesCollectionWriter create(File file) throws IOException {
    if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
      throw new FileNotFoundException(String.format("Parent directory '%s' does not exist and could not be created", file.getParentFile()));
    }
    return new CsvFileToxicitiesCollectionWriter(new FileWriter(file));
  }

  @Override
  public void write(FileToxicitiesCollection fileToxicitiesCollection) throws IOException {
    List<String> columnHeadings = new ArrayList<>();
    columnHeadings.add(FILE_COLUMN_NAME);
    columnHeadings.add(TOXICITY_COLUMN_NAME);
    List<String> metricTypes = fileToxicitiesCollection.metricTypes().stream()
        .collect(Collectors.toList());
    metricTypes.sort(String::compareTo);
    columnHeadings.addAll(metricTypes);

    List<String> lines = new ArrayList<>();
    lines.add(columnsToRow(columnHeadings));
    lines.addAll(fileToxicitiesCollection.stream()
        .map(f -> fileToxicitiesColumns(f, columnHeadings))
        .map(c -> columnsToRow(c))
        .collect(Collectors.toList()));

    String content = String.join(System.lineSeparator(), lines) + System.lineSeparator();

    writer.write(content);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }

  private static List<String> fileToxicitiesColumns(FileToxicities f, List<String> columns) {
    return columns.stream()
        .map(c -> {
          if (c.equals(FILE_COLUMN_NAME)) {
            return '"' + f.file().getPath() + '"';
          }
          if (c.equals(TOXICITY_COLUMN_NAME)) {
            return Double.toString(f.totalToxicity().value());
          }
          return Double.toString(f.metricToxicities().getOrDefault(c, Toxicity.zero()).value());
        })
        .collect(Collectors.toList());
  }

  private static String columnsToRow(List<String> columns) {
    return String.join(",", columns);
  }
}

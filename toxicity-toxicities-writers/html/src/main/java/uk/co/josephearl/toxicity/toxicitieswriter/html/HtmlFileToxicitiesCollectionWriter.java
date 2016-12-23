package uk.co.josephearl.toxicity.toxicitieswriter.html;

import uk.co.josephearl.toxicity.FileToxicities;
import uk.co.josephearl.toxicity.FileToxicitiesCollection;
import uk.co.josephearl.toxicity.FileToxicitiesCollectionWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class HtmlFileToxicitiesCollectionWriter implements FileToxicitiesCollectionWriter {
  private final File directory;

  private HtmlFileToxicitiesCollectionWriter(File directory) {
    this.directory = directory;
  }

  public static HtmlFileToxicitiesCollectionWriter create(File directory) throws IOException {
    checkDirectoryIsWritable(directory);
    return new HtmlFileToxicitiesCollectionWriter(directory);
  }

  @Override
  public void write(FileToxicitiesCollection content) throws IOException {
    writeToxicitiesJs("toxicities.js", directory, content);
    writeThresholdsJs("thresholds.js", directory, content);
    copyResource("index.html", directory);
    copyResource("d3.v3.min.js", directory);
    copyResource("jquery-1.9.1.min.js", directory);
    copyResource("toxicity.css", directory);
    copyResource("toxicity.js", directory);
  }

  @Override
  public void close() throws IOException {
  }

  private static void writeToxicitiesJs(String name, File directory, FileToxicitiesCollection fileToxicitiesCollection) throws IOException {
    File toxicitiesJsFile = new File(directory, name);

    String toxicitiesJs = fileToxicitiesCollection.stream()
        .map(f -> fileToxicitiesJson(f, fileToxicitiesCollection.metricTypes()))
        .collect(Collectors.collectingAndThen(toJsonArray(), json -> toJsonp("toxicities", json)))
        + System.lineSeparator();

    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(toxicitiesJsFile), StandardCharsets.UTF_8)) {
      writer.write(toxicitiesJs);
    }
  }

  private static void writeThresholdsJs(String name, File directory, FileToxicitiesCollection fileToxicitiesCollection) throws IOException {
    File thresholdsJsFile = new File(directory, name);

    String thresholdsJs = fileToxicitiesCollection.metricTypes().stream()
        .map(s -> "\"" + s + "\"")
        .collect(Collectors.collectingAndThen(toJsonArray(), json -> toJsonp("thresholds", json)))
        + System.lineSeparator();

    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(thresholdsJsFile), StandardCharsets.UTF_8)) {
      writer.write(thresholdsJs);
    }
  }

  private static String fileToxicitiesJson(FileToxicities fileToxicities, Set<String> metricTypes) {
    String toxicities = fileToxicities.metricToxicities().entrySet().stream()
        .map(e -> "\"" + e.getKey() + "\":" + e.getValue().value())
        .collect(Collectors.collectingAndThen(Collectors.toList(), l -> String.join(",", l)));

    return "{"
        + "\"path\":\"" + fileToxicities.file().getPath() + "\"" + ","
        + "\"name\":\"" + fileToxicities.file().getName() + "\"" + ","
        + "\"total\":" + fileToxicities.totalToxicity().value() + ","
        + "\"toxicities\": {" + toxicities + "}"
        + "}";
  }

  private static Collector<String, ?, String> toJsonArray() {
    return Collectors.collectingAndThen(Collectors.toList(), l -> "[" + String.join(",", l) + "]");
  }

  private static String toJsonp(String callbackName, String json) {
    return "function " + callbackName + "() { return " + json + "; }";
  }

  private static void copyResource(String name, File directory) throws IOException {
    InputStream from = HtmlFileToxicitiesCollectionWriter.class.getResourceAsStream(name);
    Path to = new File(directory, name).toPath();
    Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
  }

  private static void checkDirectoryIsWritable(File directory) throws IOException {
    if (directory.exists() && !directory.isDirectory()) {
      throw new FileNotFoundException(String.format("File '%s' is a file, not a directory", directory));
    }
    if (!directory.exists() && !directory.mkdirs()) {
      throw new FileNotFoundException(String.format("Could not create directory '%s'", directory));
    }
  }
}

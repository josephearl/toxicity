package uk.co.josephearl.toxicity.thresholdsreader.eslint;

import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;
import uk.co.josephearl.toxicity.ThresholdsReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.json.*;

public class ESLintThresholdsReader implements ThresholdsReader {
  private final JsonReader jsonReader;

  private ESLintThresholdsReader(JsonReader jsonReader) {
    this.jsonReader = jsonReader;
  }

  public static ESLintThresholdsReader create(File file) throws IOException {
    return new ESLintThresholdsReader(Json.createReader(new FileInputStream(file)));
  }

  @Override
  public Thresholds read() throws IOException {
    JsonObject rootObject = jsonReader.readObject();
    JsonObject rulesObject = getRulesObject(rootObject);
    return rulesObject.entrySet().stream()
        .map(ruleObject -> {
          String metricType = ruleObject.getKey();
          JsonArray ruleProperties = (JsonArray) ruleObject.getValue();
          int max = parseMax(ruleProperties);
          return Threshold.of(Metric.of(metricType, max));
        })
        .collect(Collectors.collectingAndThen(Collectors.toSet(), Thresholds::of));
  }

  @Override
  public void close() throws IOException {
    jsonReader.close();
  }

  private JsonObject getRulesObject(JsonObject rootObject) throws IOException {
    if (!rootObject.containsKey("rules")) throw new IOException("Could not find rules");
    return rootObject.getJsonObject("rules");
  }

  private int parseMax(JsonArray jsonArray) {
    return jsonArray.stream()
        .filter(jsonValue -> isJsonObject(jsonValue) && jsonObject(jsonValue).containsKey("max"))
        .findFirst()
        .map(jsonValue -> jsonObject(jsonValue))
        .map(jsonObject -> jsonObject.getInt("max", 1))
        .orElse(1);
  }

  private boolean isJsonObject(JsonValue jsonValue) {
    return jsonValue.getValueType() == JsonValue.ValueType.OBJECT;
  }

  private JsonObject jsonObject(JsonValue jsonValue) {
    return (JsonObject) jsonValue;
  }
}

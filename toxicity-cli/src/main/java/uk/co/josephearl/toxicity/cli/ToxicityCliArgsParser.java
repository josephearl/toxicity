package uk.co.josephearl.toxicity.cli;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;

final class ToxicityCliArgsParser {
  private final String name;

  private ToxicityCliArgsParser(String name) {
    this.name = name;
  }

  static ToxicityCliArgsParser named(String name) {
    return new ToxicityCliArgsParser(name);
  }

  String name() {
    return name;
  }

  ToxicityCliArgs parse(String[] args) throws ToxicityCliArgsParseException {
    ArgumentParser parser = createToxicityArgumentParser(name);

    Namespace parsedArguments = parseArguments(parser, args);

    return ToxicityCliArgs.builder()
        .thresholdsFile(new File(parsedArguments.getString("thresholds_file")))
        .thresholdsFileFormat(parsedArguments.get("thresholds_format"))
        .metricsFile(new File(parsedArguments.getString("metrics_file")))
        .metricsFileFormat(parsedArguments.get("metrics_format"))
        .toxicitiesFile(new File(parsedArguments.getString("toxicities_file")))
        .toxicitiesFileFormat(parsedArguments.get("toxicities_format"))
        .build();
  }

  private static ArgumentParser createToxicityArgumentParser(String name) {
    ArgumentParser parser = ArgumentParsers.newArgumentParser(name, false)
        .defaultHelp(true)
        .description("How toxic is your code? Find out.");

    addThresholdsFileArgument(parser);
    addMetricsFileArgument(parser);
    addToxicitiesFileArgument(parser);

    return parser;
  }

  private static ArgumentParser addThresholdsFileArgument(ArgumentParser parser) {
    parser.addArgument("thresholds-file")
        .required(true)
        .help("File to createReader thresholds from");
    parser.addArgument("-t", "--thresholds-format")
        .type(Arguments.enumStringType(ThresholdsFormat.class))
        .setDefault(ThresholdsFormat.CHECKSTYLE)
        .help("Thresholds file format");
    return parser;
  }

  private static ArgumentParser addMetricsFileArgument(ArgumentParser parser) {
    parser.addArgument("metrics-file")
        .required(true)
        .help("File to createReader metrics from");
    parser.addArgument("-m", "--metrics-format")
        .type(Arguments.enumStringType(MetricsFormat.class))
        .setDefault(MetricsFormat.CHECKSTYLE)
        .help("Metrics file format");
    return parser;
  }

  private static ArgumentParser addToxicitiesFileArgument(ArgumentParser parser) {
    parser.addArgument("toxicities-file")
        .required(true)
        .help("File to write toxicities to");
    parser.addArgument("-o", "--toxicities-format")
        .type(Arguments.enumStringType(ToxicitiesFormat.class))
        .setDefault(ToxicitiesFormat.CSV)
        .help("Toxicities file format");
    return parser;
  }

  private static Namespace parseArguments(ArgumentParser parser, String[] args) throws ToxicityCliArgsParseException {
    try {
      return parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      throw new ToxicityCliArgsParseException(e);
    }
  }
}

package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.GradleException;
import org.gradle.api.reporting.Report;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

abstract class ToxicityInvoker {
  public static void invoke(Toxicity toxicityTask) {
    String javaBinary = System.getProperty("java.home") + System.getProperty("file.separator")
        + "bin" + System.getProperty("file.separator") + "java";

    String toxicityClassPath = String.join(System.getProperty("path.separator"),
        toxicityTask.getToxicityClasspath().getFiles().stream().map(File::getPath).collect(Collectors.toSet()));

    Report report = toxicityTask.getReports().getEnabled().iterator().next();

    ProcessBuilder processBuilder =
        new ProcessBuilder(javaBinary,
            "-cp", toxicityClassPath,
            "uk.co.josephearl.toxicity.cli.ToxicityCli",
            "--thresholds-format", "checkstyle",
            "--metrics-format", "checkstyle",
            "--toxicities-format", report.getName(),
            toxicityTask.getConfigFile().getPath(),
            toxicityTask.getCheckstyleXmlReportFile().getPath(),
            report.getDestination().getPath());

    processBuilder.redirectErrorStream(true);

    StringBuilder stdOutBuilder = new StringBuilder();

    try {
      Process process = processBuilder.start();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          stdOutBuilder.append(line);
        }
      }
      int exitCode = process.waitFor();
      if (exitCode != 0) {
        throw new GradleException("ToxicityCli exited with non-zero exit code " + exitCode + " (" + stdOutBuilder + ")");
      }
    } catch (InterruptedException | IOException e) {
      throw new GradleException("Error invoking ToxicityCli", e);
    }
  }
}

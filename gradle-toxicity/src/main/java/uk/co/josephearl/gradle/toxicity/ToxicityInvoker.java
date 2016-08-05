package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

abstract class ToxicityInvoker {
  public static void invoke(Toxicity toxicityTask) {
    String javaBinary = System.getProperty("checkstyle.home") + System.getProperty("file.separator")
        + "bin" + System.getProperty("file.separator") + "java";

    String toxicityClassPath = String.join(System.getProperty("path.separator"),
        toxicityTask.getToxicityClasspath().getFiles().stream().map(File::getPath).collect(Collectors.toSet()));

    ProcessBuilder processBuilder =
        new ProcessBuilder(javaBinary,
            "-cp", toxicityClassPath,
            "uk.co.josephearl.toxicity.cli.ToxicityCli",
            "--thresholds-format", "checkstyle",
            "--metrics-format", "checkstyle",
            "--toxicities-format", "csv",
            toxicityTask.getConfigFile().getPath(),
            toxicityTask.getCheckstyleXmlReportFile().getPath(),
            toxicityTask.getReports().getCsv().getDestination().getPath());
    processBuilder.inheritIO();

    try {
      Process process = processBuilder.start();
      int exitCode = process.waitFor();
      if (exitCode != 0) {
        throw new GradleException("ToxicityCli exited with non-zero exit code " + exitCode);
      }
    } catch (InterruptedException | IOException e) {
      throw new GradleException("Error invoking ToxicityCli", e);
    }
  }
}

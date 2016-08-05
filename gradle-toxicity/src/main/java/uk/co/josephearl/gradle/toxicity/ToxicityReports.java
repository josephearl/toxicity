package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.Task;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.reporting.internal.TaskGeneratedSingleFileReport;
import org.gradle.api.reporting.internal.TaskReportContainer;

public class ToxicityReports extends TaskReportContainer<SingleFileReport> {
  public ToxicityReports(Task task) {
    super(SingleFileReport.class, task);

    add(TaskGeneratedSingleFileReport.class, "csv", task);
  }

  public SingleFileReport getCsv() {
    return getByName("csv");
  }
}

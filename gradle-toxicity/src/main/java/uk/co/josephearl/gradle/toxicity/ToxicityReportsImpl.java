package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.Task;
import org.gradle.api.reporting.ConfigurableReport;
import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.reporting.Report;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.reporting.internal.TaskGeneratedSingleDirectoryReport;
import org.gradle.api.reporting.internal.TaskGeneratedSingleFileReport;
import org.gradle.api.reporting.internal.TaskReportContainer;

class ToxicityReportsImpl extends TaskReportContainer<Report> implements ToxicityReports {
  public ToxicityReportsImpl(Task task) {
    super(ConfigurableReport.class, task);

    add(TaskGeneratedSingleDirectoryReport.class, "html", task, "toxicity.html");
    add(TaskGeneratedSingleFileReport.class, "csv", task);
  }

  @Override
  public DirectoryReport getHtml() {
    return (DirectoryReport) getByName("html");
  }

  @Override
  public SingleFileReport getCsv() {
    return (SingleFileReport) getByName("csv");
  }
}

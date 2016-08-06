package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.reporting.Report;
import org.gradle.api.reporting.ReportContainer;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.tasks.Nested;

public interface ToxicityReports extends ReportContainer<Report> {
  @Nested
  DirectoryReport getHtml();

  @Nested
  SingleFileReport getCsv();
}

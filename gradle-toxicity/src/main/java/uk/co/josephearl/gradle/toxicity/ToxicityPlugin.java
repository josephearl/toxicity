/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.josephearl.gradle.toxicity;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.internal.IConventionAware;
import org.gradle.api.plugins.quality.CodeQualityExtension;
import org.gradle.api.plugins.quality.internal.AbstractCodeQualityPlugin;
import org.gradle.api.reporting.Report;
import org.gradle.api.tasks.SourceSet;

import java.io.File;

/**
 * Toxicity Plugin.
 */
public class ToxicityPlugin extends AbstractCodeQualityPlugin<Toxicity> {
  public static final String DEFAULT_TOXICITY_VERSION = "1.0.0-SNAPSHOT";
  public static final String DEFAULT_CHECKSTYLE_VERSION = "5.9";

  private ToxicityExtension extension;

  protected static ConventionMapping conventionMappingOf(Object object) {
    return ((IConventionAware) object).getConventionMapping();
  }

  @Override
  protected String getToolName() {
    return "Toxicity";
  }

  @Override
  protected Class<Toxicity> getTaskType() {
    return Toxicity.class;
  }

  @Override
  protected CodeQualityExtension createExtension() {
    extension = project.getExtensions().create("toxicity", ToxicityExtension.class, project);
    extension.setToolVersion(DEFAULT_TOXICITY_VERSION);
    extension.setCheckstyleVersion(DEFAULT_CHECKSTYLE_VERSION);
    extension.setConfig(project.getResources().getText().fromFile("config/toxicity/checkstyle.xml"));
    return extension;
  }

  @Override
  protected void configureTaskDefaults(Toxicity task, final String baseName) {
    Configuration configuration = project.getConfigurations().getAt("toxicity");
    configureDefaultDependencies(configuration);
    configureTaskConventionMapping(configuration, task);
    configureReportsConventionMapping(task, baseName);
  }

  private void configureDefaultDependencies(Configuration configuration) {
    configuration.defaultDependencies(dependencies -> {
        dependencies.add(project.getDependencies().create("uk.co.josephearl.toxicity:toxicity-cli:" + extension.getToolVersion()));
        dependencies.add(project.getDependencies().create("com.puppycrawl.tools:checkstyle:" + extension.getCheckstyleVersion()));
    });
  }

  private void configureTaskConventionMapping(Configuration configuration, Toxicity task) {
    ConventionMapping taskMapping = task.getConventionMapping();
    taskMapping.map("toxicityClasspath", () -> configuration);
    taskMapping.map("checkstyleClasspath", () -> configuration);
    taskMapping.map("config", () -> extension.getConfig());
    taskMapping.map("configProperties", () -> extension.getConfigProperties());
    //taskMapping.map("ignoreFailures", () -> extension.isIgnoreFailures());
  }

  private void configureReportsConventionMapping(Toxicity task, final String baseName) {
    Report csvReport = task.getReports().getCsv();
    ConventionMapping csvReportMapping = conventionMappingOf(csvReport);
    csvReportMapping.map("enabled", () -> true);
    csvReportMapping.map("destination", () -> new File(extension.getReportsDir(), baseName + "." + csvReport.getName()));

    Report htmlReport = task.getReports().getHtml();
    ConventionMapping htmlReportMapping = conventionMappingOf(htmlReport);
    htmlReportMapping.map("enabled", () -> false);
    htmlReportMapping.map("destination", () -> new File(extension.getReportsDir(), baseName + "-" + htmlReport.getName()));
  }

  @Override
  protected void configureForSourceSet(final SourceSet sourceSet, Toxicity task) {
    task.setDescription("Run Toxicity analysis for " + sourceSet.getName() + " classes");
    task.setClasspath(sourceSet.getOutput());
    task.setSource(sourceSet.getAllJava());
  }
}

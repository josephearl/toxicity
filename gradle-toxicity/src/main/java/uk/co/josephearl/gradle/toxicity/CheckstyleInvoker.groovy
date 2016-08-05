/*
 * Copyright 2016 the original author or authors.
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

package uk.co.josephearl.gradle.toxicity

import org.gradle.api.file.FileCollection

abstract class CheckstyleInvoker {
  private static final String FAILURE_PROPERTY_NAME = 'org.gradle.checkstyle.violations'

  static void invoke(Toxicity toxicityTask) {
    def antBuilder = toxicityTask.antBuilder
    def checkstyleClasspath = toxicityTask.checkstyleClasspath
    def source = toxicityTask.source
    def classpath = toxicityTask.classpath
    def configProperties = toxicityTask.configProperties
    def config = toxicityTask.config
    def report = File.createTempFile("checkstyle-report", ".xml")

    antBuilder.withClasspath(checkstyleClasspath).execute {
      try {
        ant.taskdef(name: 'checkstyle', classname: 'com.puppycrawl.tools.checkstyle.CheckStyleTask')
      } catch (RuntimeException ignore) {
        ant.taskdef(name: 'checkstyle', classname: 'com.puppycrawl.tools.checkstyle.ant.CheckstyleAntTask')
      }

      ant.checkstyle(config: config.asFile(), failOnViolation: false, failureProperty: FAILURE_PROPERTY_NAME) {
        source.addToAntBuilder(ant, 'fileset', FileCollection.AntType.FileSet)
        classpath.addToAntBuilder(ant, 'classpath')

        formatter(type: 'xml', toFile: report)

        configProperties.each { key, value ->
          property(key: key, value: value.toString())
        }
      }
    }

    toxicityTask.setCheckstyleXmlReportFile(report);
  }
}

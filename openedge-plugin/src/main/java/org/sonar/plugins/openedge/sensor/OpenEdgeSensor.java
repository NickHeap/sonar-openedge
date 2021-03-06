/*
 * OpenEdge plugin for SonarQube
 * Copyright (C) 2013-2016 Riverside Software
 * contact AT riverside DASH software DOT fr
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.openedge.sensor;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.plugins.openedge.foundation.OpenEdge;
import org.sonar.plugins.openedge.foundation.OpenEdgeMetrics;

public class OpenEdgeSensor implements Sensor {
  private static final Logger LOG = LoggerFactory.getLogger(OpenEdgeSensor.class);

  private final FileSystem fileSystem;

  public OpenEdgeSensor(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.onlyOnLanguage(OpenEdge.KEY).name(getClass().getSimpleName());
  }

  @Override
  public void execute(SensorContext context) {
    computeBaseMetrics(context);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void computeBaseMetrics(SensorContext context) {
    for (InputFile file : fileSystem.inputFiles(fileSystem.predicates().hasLanguage(OpenEdge.KEY))) {
      LOG.trace("Computing basic metrics on {}", file.relativePath());
      // Depending on file extension
      String fileExt = FilenameUtils.getExtension(file.relativePath());
      if ("w".equalsIgnoreCase(fileExt)) {
        context.newMeasure().on(file).withValue(1).forMetric((Metric) OpenEdgeMetrics.WINDOWS).save();
      } else if ("p".equalsIgnoreCase(fileExt)) {
        context.newMeasure().on(file).withValue(1).forMetric((Metric) OpenEdgeMetrics.PROCEDURES).save();
      } else if ("i".equalsIgnoreCase(fileExt)) {
        context.newMeasure().on(file).withValue(1).forMetric((Metric) OpenEdgeMetrics.INCLUDES).save();
      } else if ("cls".equalsIgnoreCase(fileExt)) {
        context.newMeasure().on(file).withValue(1).forMetric((Metric) OpenEdgeMetrics.CLASSES).save();
      }
    }
  }

}

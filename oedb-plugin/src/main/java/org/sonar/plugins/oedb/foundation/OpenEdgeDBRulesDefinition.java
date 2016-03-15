/*
 * OpenEdge DB plugin for SonarQube
 * Copyright (C) 2015 Riverside Software
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
package org.sonar.plugins.oedb.foundation;

import java.util.Arrays;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.oedb.api.AnnotationBasedRulesDefinition;

public class OpenEdgeDBRulesDefinition implements RulesDefinition {
  public static final String REPOSITORY_KEY = "rssw-oedb";
  public static final String REPOSITORY_NAME = "Riverside Software DB Rules";

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, OpenEdgeDB.KEY).setName(REPOSITORY_NAME);

    AnnotationBasedRulesDefinition annotationLoader = new AnnotationBasedRulesDefinition(repository, OpenEdgeDB.KEY);
    annotationLoader.addRuleClasses(false, false, Arrays.<Class> asList(OpenEdgeDBRulesRegistrar.checkClasses()));
    repository.done();
  }
}
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/** Abstract Maven plugin base class for generating {@code package-info.java} files with JSpecify annotation. */
public abstract class AbstractJSpecifyPackageInfoGeneratorMojo extends AbstractMojo {

    /** Represents the Maven project. */
    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;
    /** Whether to skip the execution of this plugin. */
    @Parameter(property = "skip", defaultValue = "false")
    protected boolean skip;
    /** The type of JSpecify annotation to be added to the generated {@code package-info.java} files. */
    @Parameter(property = "annotation", defaultValue = "NULL_MARKED")
    protected JSpecifyAnnotationType annotation;

}
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
package dev.bencai.jspecifyutilities.packageinfogenerator.configuration;

import lombok.Data;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;

/**
 * Context class for generating {@code package-info.java} files during the Maven plugin execution process.
 * <p>This class encapsulates the configuration and resources required to facilitate the generation of annotated {@code package-info.java} files.</p>
 */
@Data
public class PackageInfoGeneratorContext {

    /** The MavenProject instance representing the current Maven project. */
    private final MavenProject project;
    /** The logging instance provided by the Maven plugin. */
    private final Log log;
    /** A flag indicating whether the generation process should be skipped. */
    private final boolean skip;
    /** The JSpecifyAnnotationType specifying the null-safety annotation type. */
    private final JSpecifyAnnotationType annotation;
    /** The path to the source directory of the Java files. */
    private final Path sourcesDirectory;
    /** The path to the directory where the generated {@code {@code package-info.java}} files will be stored. */
    private final Path generatedSourcesDirectory;

}
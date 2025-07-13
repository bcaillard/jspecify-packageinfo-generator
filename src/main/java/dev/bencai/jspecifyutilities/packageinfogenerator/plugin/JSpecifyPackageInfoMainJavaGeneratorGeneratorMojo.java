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
package dev.bencai.jspecifyutilities.packageinfogenerator.plugin;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_JAVA_DIRECTORY;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_OUTPUT_DIRECTORY;

/** Maven plugin for generating {@code package-info.java} files with JSpecify annotation from java main source directory. */
@Mojo(name = "generate-package-info", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo extends AbstractJSpecifyPackageInfoGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException {
        final Path sourceDirectory = Paths.get(project.getBasedir().getPath(), MAIN_JAVA_DIRECTORY);
        final Path generatedSourcesDirectory = Paths.get(project.getBuild().getDirectory(), MAIN_OUTPUT_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(project, getLog(), skip, annotation, sourceDirectory, generatedSourcesDirectory);

        PackageInfoGeneratorEngine.generate(context);
    }

}
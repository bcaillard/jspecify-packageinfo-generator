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

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.TEST_JAVA_DIRECTORY;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.TEST_OUTPUT_DIRECTORY;

/** Maven plugin for generating {@code package-info.java} files with JSpecify annotation from java test source directory. */
@Mojo(name = "generate-package-info-test", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES)
public class JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo extends AbstractJSpecifyPackageInfoGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException {
        final Path testSourceDirectory = Paths.get(project.getBasedir().getPath(), TEST_JAVA_DIRECTORY);
        final Path generatedTestSourcesDirectory = Paths.get(project.getBuild().getDirectory(), TEST_OUTPUT_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(project, getLog(), skip, annotation, testSourceDirectory, generatedTestSourcesDirectory);

        PackageInfoGeneratorEngine.generate(context);
    }

}
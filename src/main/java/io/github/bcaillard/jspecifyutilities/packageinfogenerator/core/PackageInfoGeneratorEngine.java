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
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.PackageInfoFileWriter;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;

/** Class responsible for generating {@code package-info.java} files in a Java project. */
@UtilityClass
public class PackageInfoGeneratorEngine {

    /**
     * Generates {@code package-info.java} files for the specified source directory in the given context.
     *
     * @param context the context containing configuration and resources required for the generation of
     * {@code package-info.java} files, including the sources directory, logging mechanism,
     * and annotations.
     *
     * @throws MojoExecutionException if an error occurs during the generation process, such as failure
     * to walk through the source directory or create the package information files.
     */
    public static void generate(final PackageInfoGeneratorContext context) throws MojoExecutionException {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();

        if (context.isSkip()) {
            logger.info("Skipping package-info.java generation because property 'skip' is set to true");
        } else if (!Files.exists(sourcesDirectory)) {
            logger.info("Skipping package-info.java generation because there is no sources directory");
        } else {
            logger.debug("Generating package-info.java files");
            try {
                final io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.JavaPackageVisitor javaPackageVisitor = new io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.JavaPackageVisitor(logger, p -> PackageInfoFileWriter.createPackageInfo(context, p));
                Files.walkFileTree(sourcesDirectory, javaPackageVisitor);
                logger.debug("All package-info.java files have been generated");
            } catch (final Exception ex) {
                throw new MojoExecutionException("Failed to generate package-info.java files. Error occurred during files generation process", ex);
            }
        }
    }

}
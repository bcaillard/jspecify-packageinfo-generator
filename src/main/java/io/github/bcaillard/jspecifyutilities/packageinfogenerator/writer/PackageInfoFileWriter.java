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
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.PACKAGE_INFO_FILENAME;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.PACKAGE_SEPARATOR;

/**
 * Utility class responsible for creating and writing {@code package-info.java} files for specified Java packages.
 * This class aims to facilitate the automatic generation of annotated {@code package-info.java} files during build processes.
 */
@UtilityClass
public class PackageInfoFileWriter {

    /**
     * Creates a {@code package-info.java} file for a specified Java package and writes it to the appropriate directory in the generated sources.
     *
     * @param context the context containing configuration and resources needed for package info generation, such as source and output directories, annotations, and logging utilities
     * @param fromJavaPackage the path representing the full directory of the Java package for which the {@code package-info.java} file will be created
     *
     * @throws io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.CantCreatePackageInfoException if an exception occurs during the creation or writing of the {@code package-info.java}
     * file
     */
    public static void createPackageInfo(final PackageInfoGeneratorContext context, final Path fromJavaPackage) {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();

        logger.debug("Java package fullpath: " + fromJavaPackage);
        final String packagePath = fromJavaPackage.toString()
                                                  .replace(sourcesDirectory.toString(), "")
                                                  .replaceFirst(File.separator, "");
        logger.debug("Java package path only: " + packagePath);

        final Path packageInfoFolder = context.getGeneratedSourcesDirectory().resolve(Paths.get(packagePath));
        logger.debug("Generated directory: " + packageInfoFolder);
        final Path packageInfoFile = packageInfoFolder.resolve(PACKAGE_INFO_FILENAME);
        logger.debug("Generated Package Info: " + packageInfoFile);

        // Détermine le nom du package à partir du chemin source
        final String packageNameOnly = packagePath.replace(File.separatorChar, PACKAGE_SEPARATOR);
        logger.debug("Java package of Package Info: " + packageNameOnly);

        // Écriture du contenu dans package-info.java
        try {
            Files.createDirectories(packageInfoFolder);
            logger.debug(String.format("Directories created: %s", packageInfoFolder));

            Files.createFile(packageInfoFile);
            logger.debug(String.format("Empty package-info.java created: %s", packageInfoFile));

            Files.write(packageInfoFile, io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.PackageInfoTemplateFileProvider.provideContent(context.getAnnotation()
                                                                                                                                                                 .getAnnotationName(), packageNameOnly));
            logger.info(String.format("Created package-info.java with @%s annotation in package: %s", context.getAnnotation().getAnnotationName(), packageNameOnly));
        } catch (final IOException ioe) {
            logger.error(String.format("Failed to write package-info.java files: %s", ioe.getMessage()));
            throw new io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.CantCreatePackageInfoException("Failed to write package-info.java files", ioe);
        }
    }

}
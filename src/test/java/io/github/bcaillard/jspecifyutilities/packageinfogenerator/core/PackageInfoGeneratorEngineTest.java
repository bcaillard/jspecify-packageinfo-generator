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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/** Tests for {@link PackageInfoGeneratorEngineTest}. */
@ExtendWith(MockitoExtension.class)
class PackageInfoGeneratorEngineTest {

    @Mock
    private Log mockLog;
    @Mock
    private Path mockSourceDirectory;

    @Test
    void should_skip_generation_when_skip_is_true() throws MojoExecutionException {
        try (final MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            // Arrange
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, true, null, null, null);

            // Act
            io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine.generate(context);

            // Assert
            filesMockedStatic.verifyNoInteractions();
            verify(mockLog).info("Skipping package-info.java generation because property 'skip' is set to true");
        }
    }

    @Test
    void should_skip_generation_when_source_directory_does_not_exist() throws MojoExecutionException {
        try (final MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            // Arrange
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, null, mockSourceDirectory, null);

            filesMockedStatic.when(() -> Files.exists(mockSourceDirectory)).thenReturn(false);

            // Act
            io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine.generate(context);

            // Assert
            filesMockedStatic.verify(() -> Files.walkFileTree(any(Path.class), any(io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.JavaPackageVisitor.class)), never());
            verify(mockLog).info("Skipping package-info.java generation because there is no sources directory");
        }
    }

    @Test
    void should_generate_package_info_files_when_source_directory_exists() throws MojoExecutionException {
        try (final MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            // Arrange
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, null, mockSourceDirectory, null);

            filesMockedStatic.when(() -> Files.exists(mockSourceDirectory)).thenReturn(true);

            // Act
            io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine.generate(context);

            // Assert
            filesMockedStatic.verify(() -> Files.walkFileTree(any(Path.class), any(io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.JavaPackageVisitor.class)));
            verify(mockLog).debug("Generating package-info.java files");
            verify(mockLog).debug("All package-info.java files have been generated");
        }
    }

    @Test
    void should_throw_MojoExecutionException_when_generation_fails() {
        try (final MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            // Arrange
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, null, mockSourceDirectory, null);

            filesMockedStatic.when(() -> Files.exists(mockSourceDirectory)).thenReturn(true);
            filesMockedStatic.when(() -> Files.walkFileTree(any(Path.class), any(io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.JavaPackageVisitor.class)))
                             .thenThrow(new IllegalArgumentException("Test exception"));

            // Act & Assert
            Assertions.assertThatCode(() -> io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine.generate(context))
                      .isInstanceOf(MojoExecutionException.class)
                      .hasMessage("Failed to generate package-info.java files. Error occurred during files generation process");
        }
    }

}
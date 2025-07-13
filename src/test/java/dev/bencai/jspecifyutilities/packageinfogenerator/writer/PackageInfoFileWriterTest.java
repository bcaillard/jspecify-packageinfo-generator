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
package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;

/** Test class for {@link PackageInfoFileWriter}. */
@ExtendWith(MockitoExtension.class)
class PackageInfoFileWriterTest {

    @Mock
    private Log mockLog;
    @TempDir
    private Path tmpSourceDirectory;
    @TempDir
    private Path tmpTargetDirectory;

    @Test
    void should_create_package_info_file() {
        // Arrange
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, JSpecifyAnnotationType.NULL_MARKED, tmpSourceDirectory, tmpTargetDirectory);
        final Path javaPackage = tmpSourceDirectory.resolve("src/main/java/foo/bar");

        // Act
        PackageInfoFileWriter.createPackageInfo(context, javaPackage);

        // Assert
        final Path packageInfoCreated = tmpTargetDirectory.resolve("src/main/java/foo/bar/package-info.java");
        Assertions.assertThat(Files.exists(packageInfoCreated)).isTrue();
    }

    @Test
    void should_throw_CantCreatePackageInfoException_when_IOException() {
        try (final MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            // Arrange
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, JSpecifyAnnotationType.NULL_MARKED, tmpSourceDirectory, tmpTargetDirectory);
            final Path javaPackage = tmpSourceDirectory.resolve("src/main/java/foo/bar");

            filesMockedStatic.when(() -> Files.createDirectories(any(Path.class)))
                             .thenThrow(new AccessDeniedException("access denied"));

            // Act & Assert
            Assertions.assertThatCode(() -> PackageInfoFileWriter.createPackageInfo(context, javaPackage))
                      .isInstanceOf(CantCreatePackageInfoException.class)
                      .hasMessage("Failed to write package-info.java files");
        }
    }

}
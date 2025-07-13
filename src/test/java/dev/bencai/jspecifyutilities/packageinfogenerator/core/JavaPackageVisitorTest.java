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
package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/** Tests for {@link JavaPackageVisitor}. */
@ExtendWith(MockitoExtension.class)
class JavaPackageVisitorTest {

    @InjectMocks
    private JavaPackageVisitor javaPackageVisitor;
    @Mock
    @SuppressWarnings("unused")
    private Log mockLog;
    @Mock
    private BasicFileAttributes mockAttributes;
    @Mock
    private Consumer<Path> consumer;
    @TempDir
    private Path tmpDir;

    @Test
    void should_skip_java_package_when_it_already_contains_PackageInfo() throws IOException {
        // Arrange
        final Path packageInfoFile = tmpDir.resolve(PackageInfoGeneratorConstants.PACKAGE_INFO_FILENAME);
        Files.createFile(packageInfoFile);

        // Act
        final FileVisitResult actual = javaPackageVisitor.preVisitDirectory(tmpDir, mockAttributes);

        // Assert
        assertThat(actual).isEqualTo(FileVisitResult.CONTINUE);
        verifyNoInteractions(consumer);
    }

    @Test
    void should_skip_directory_and_continue_when_there_are_no_java_files() throws IOException {
        // Act
        final FileVisitResult actual = javaPackageVisitor.preVisitDirectory(tmpDir, mockAttributes);

        // Assert
        assertThat(actual).isEqualTo(FileVisitResult.CONTINUE);
        verifyNoInteractions(consumer);
    }

    @Test
    void should_process_directory_and_continue_when_PackageInfo_is_absent_and_it_contains_at_least_one_java_file() throws IOException {
        // Arrange
        final Path packageInfoFile = tmpDir.resolve("App.java");
        Files.createFile(packageInfoFile);

        // Act
        final FileVisitResult actual = javaPackageVisitor.preVisitDirectory(tmpDir, mockAttributes);

        // Assert
        assertThat(actual).isEqualTo(FileVisitResult.CONTINUE);
        verify(consumer).accept(tmpDir);
    }

}
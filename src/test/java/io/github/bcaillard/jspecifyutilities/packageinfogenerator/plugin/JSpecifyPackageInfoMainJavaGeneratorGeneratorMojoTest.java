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
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_JAVA_DIRECTORY;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_OUTPUT_DIRECTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/** Tests for {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo}. */
@ExtendWith(MockitoExtension.class)
class JSpecifyPackageInfoMainJavaGeneratorGeneratorMojoTest {

    @Mock
    private MavenProject mockProject;
    @Mock
    private Log mockLog;
    @Mock
    private Build mockBuild;

    @Test
    void should_invoke_Engine_run_with_correct_context() throws Exception {
        // Arrange
        when(mockProject.getBasedir()).thenReturn(Paths.get("/project/base").toFile());
        when(mockProject.getBuild()).thenReturn(mockBuild);
        when(mockBuild.getDirectory()).thenReturn("/project/build");

        final io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo mojo = new io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo();
        mojo.project = mockProject;
        mojo.skip = false;
        mojo.annotation = JSpecifyAnnotationType.NULL_MARKED;
        mojo.setLog(mockLog);

        final Path expectedSourceDirectory = Paths.get("/project/base", MAIN_JAVA_DIRECTORY);
        final Path expectedGeneratedSourcesDirectory = Paths.get("/project/build", MAIN_OUTPUT_DIRECTORY);

        try (final MockedStatic<PackageInfoGeneratorEngine> mockedEngine = Mockito.mockStatic(PackageInfoGeneratorEngine.class)) {
            // Act
            mojo.execute();

            // Assert
            mockedEngine.verify(() -> PackageInfoGeneratorEngine.generate(argThat(context -> {
                assertThat(context).isNotNull();
                assertThat(context.getProject()).isEqualTo(mockProject);
                assertThat(context.getLog()).isEqualTo(mockLog);
                assertThat(context.isSkip()).isFalse();
                assertThat(context.getAnnotation()).isEqualTo(JSpecifyAnnotationType.NULL_MARKED);
                assertThat(context.getSourcesDirectory()).isEqualTo(expectedSourceDirectory);
                assertThat(context.getGeneratedSourcesDirectory()).isEqualTo(expectedGeneratedSourcesDirectory);
                return true;
            })), times(1));
        }
    }

}
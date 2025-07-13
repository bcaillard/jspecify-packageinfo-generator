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
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.integrationtest;

import com.soebes.itf.extension.assertj.MavenExecutionResultAssert;
import com.soebes.itf.jupiter.extension.MavenCLIOptions;
import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenOption;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.soebes.itf.jupiter.maven.MavenProjectResult;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>Tests for {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo} and
 * {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo}.</p>
 * <p>Should generate {@code package-info.java} files for main and test sources directories.</p>
 */
@MavenJupiterExtension
class PackageInfoMainJavaAndTestJavaGeneratorMojoIT {

    @MavenTest
    @MavenOption(MavenCLIOptions.VERBOSE)
    @MavenGoal("clean")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info-test")
    void generate_from_main_and_test_sources_directories_test_case(final MavenExecutionResult result) throws Exception {
        MavenExecutionResultAssert.assertThat(result).isSuccessful();

        final MavenProjectResult project = result.getMavenProjectResult();
        final Path targetProjectDirectory = project.getTargetProjectDirectory();
        final Path targetGeneratedSourcesPath = targetProjectDirectory.resolve("target/generated-sources/annotations/packages-info");
        final Path targetGeneratedTestSourcesPath = targetProjectDirectory.resolve("target/generated-test-sources/test-annotations/packages-info");

        final List<Path> generatedSourcesPathToCheck = List.of(targetGeneratedSourcesPath, targetGeneratedTestSourcesPath);
        final List<String> packagesToCheck = List.of(
                "com/acme/app",
                "com/acme/app/controller",
                "com/acme/app/mapper",
                "com/acme/app/service",
                "com/acme/app/subpackage/hadpackageinfo/foo"
        );

        for (final Path generatedSourcePathToCheck : generatedSourcesPathToCheck) {
            Assertions.assertThat(countPackageInfoFiles(generatedSourcePathToCheck))
                      .as("Number of package-info.java files does not match the expected number")
                      .isEqualTo(packagesToCheck.size());

            for (final String packageToCheck : packagesToCheck) {
                final Path rootPackageInfoPath = generatedSourcePathToCheck.resolve(String.format("%s/package-info.java", packageToCheck));
                final String packageName = packageToCheck.replace("/", ".");

                Assertions.assertThat(Files.exists(rootPackageInfoPath))
                          .as("package-info.java file does not exist in %s", rootPackageInfoPath)
                          .isTrue();
                Assertions.assertThat(Files.readAllLines(rootPackageInfoPath))
                          .as("package-info.java file does not contain the expected annotations in %s", rootPackageInfoPath)
                          .containsExactly(
                                  "@org.jspecify.annotations.NullMarked",
                                  String.format("package %s;", packageName)
                          );
            }
        }
    }

    private long countPackageInfoFiles(final Path directory) throws IOException {
        try (final Stream<Path> paths = Files.walk(directory)) {
            return paths.filter(p -> p.getFileName().toString().equals(PackageInfoGeneratorConstants.PACKAGE_INFO_FILENAME))
                        .count();
        }
    }

}
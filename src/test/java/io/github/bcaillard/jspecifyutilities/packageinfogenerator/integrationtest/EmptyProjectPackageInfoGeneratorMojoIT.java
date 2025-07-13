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
import org.assertj.core.api.SoftAssertions;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Tests for {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo} and
 * {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo}.</p>
 * <p>Should ignore projets and modules without java sources directories.</p>
 */
@MavenJupiterExtension
class EmptyProjectPackageInfoGeneratorMojoIT {

    @MavenTest
    @MavenOption(MavenCLIOptions.VERBOSE)
    @MavenGoal("clean")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info-test")
    void empty_project_test_case(final MavenExecutionResult result) {
        MavenExecutionResultAssert.assertThat(result).isSuccessful();

        final MavenProjectResult project = result.getMavenProjectResult();
        final Path targetProjectDirectory = project.getTargetProjectDirectory();
        final Path targetGeneratedSourcesPath = targetProjectDirectory.resolve("target/generated-sources/annotations/packages-info");
        final Path targetGeneratedTestSourcesPath = targetProjectDirectory.resolve("target/generated-test-sources/test-annotations/packages-info");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(Files.exists(targetGeneratedSourcesPath)).isFalse();
            softly.assertThat(Files.exists(targetGeneratedTestSourcesPath)).isFalse();
        });
    }

}
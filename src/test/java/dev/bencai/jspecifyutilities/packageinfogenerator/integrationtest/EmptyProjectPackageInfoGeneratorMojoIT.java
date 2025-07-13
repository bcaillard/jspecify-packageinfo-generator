package dev.bencai.jspecifyutilities.packageinfogenerator.integrationtest;

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
 * <p>Tests for {@link dev.bencai.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo} and
 * {@link dev.bencai.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo}.</p>
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
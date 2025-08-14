package io.github.bcaillard.jspecifyutilities.packageinfogenerator.integrationtest;

import com.soebes.itf.extension.assertj.MavenExecutionResultAssert;
import com.soebes.itf.jupiter.extension.MavenCLIOptions;
import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenOption;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.extension.SystemProperty;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;

/**
 * <p>Tests for {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo} and
 * {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin.JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo}.</p>
 * <p>Should apply defined strategy when generated sources directory is not empty.</p>
 */
@MavenJupiterExtension
public class GenerationAlreadyBuiltIT {

    @MavenTest
    @MavenOption(MavenCLIOptions.VERBOSE)
    @SystemProperty(value = "whenGeneratedSourcesDirectoryExists", content = "SKIP")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    void should_skip_generation_test_case(final MavenExecutionResult result) {
        MavenExecutionResultAssert.assertThat(result)
                                  .isSuccessful()
                                  .out()
                                  .info()
                                  .contains("Skipping package-info.java generation because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=SKIP'");
    }

    @MavenTest
    @MavenOption(MavenCLIOptions.VERBOSE)
    @SystemProperty(value = "whenGeneratedSourcesDirectoryExists", content = "FAIL")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    void should_fail_generation_test_case(final MavenExecutionResult result) {
        MavenExecutionResultAssert.assertThat(result)
                                  .isFailure()
                                  .out()
                                  .plain()
                                  .contains("Caused by: org.apache.maven.plugin.MojoExecutionException: Failed because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=FAIL'");
    }

    @MavenTest
    @MavenOption(MavenCLIOptions.VERBOSE)
    @SystemProperty(value = "whenGeneratedSourcesDirectoryExists", content = "RUN")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    void should_run_generation_test_case(final MavenExecutionResult result) {
        final MavenExecutionResultAssert mavenExecutionResultAssert = MavenExecutionResultAssert.assertThat(result);
        mavenExecutionResultAssert.isSuccessful()
                                  .out()
                                  .debug()
                                  .contains("The process continues despite the existence of the generated sources directory, because 'whenGeneratedSourcesDirectoryExists=RUN'");
        mavenExecutionResultAssert.out()
                                  .info()
                                  .contains("Ignore generation for file /Users/bcaillard/Developer/Sources/jspecify-packageinfo-generator/target/maven-it/io/github/bcaillard/jspecifyutilities/packageinfogenerator/integrationtest/GenerationAlreadyBuiltIT/should_run_generation_test_case/project/target/generated-sources/annotations/packages-info/com/acme/app/package-info.java");
    }

}
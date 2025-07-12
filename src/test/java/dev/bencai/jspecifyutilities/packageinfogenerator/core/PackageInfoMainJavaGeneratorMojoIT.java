package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import com.soebes.itf.extension.assertj.MavenExecutionResultAssert;
import com.soebes.itf.jupiter.extension.MavenCLIOptions;
import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenOption;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.soebes.itf.jupiter.maven.MavenProjectResult;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@MavenJupiterExtension
class PackageInfoMainJavaGeneratorMojoIT {

    @MavenTest
//    @SystemProperty(value = "annotation", content = "NULL_UNMARKED")
    @MavenOption(MavenCLIOptions.VERBOSE)
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info")
    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:generate-package-info-test")
        //@MavenGoal("compile")
    void the_first_test_case(final MavenExecutionResult result) throws Exception {
        MavenExecutionResultAssert.assertThat(result).isSuccessful();

        final MavenProjectResult project = result.getMavenProjectResult();
        final Path targetProjectDirectory = project.getTargetProjectDirectory();
        final Path generatedFile = targetProjectDirectory
                .resolve("target/generated-sources/annotations/packages-info/com/acme/app/package-info.java");

        // target/maven-it/dev/bencai/jspecifyutilities/packageinfogenerator/core/GeneratePackageInfoMojoIT/the_first_test_case/project/target/generated-sources/annotations/packages-info/com/acme/app/package-info.java

        assertThat(Files.exists(generatedFile)).isTrue();
        assertThat(Files.readString(generatedFile)).contains("@org.jspecify.annotations.NullMarked");
    }

}
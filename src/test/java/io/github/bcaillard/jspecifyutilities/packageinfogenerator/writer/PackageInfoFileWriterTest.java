package io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources.GeneratedSourcesDirectoryExistsStrategy;
import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, JSpecifyAnnotationType.NULL_MARKED, tmpSourceDirectory, tmpTargetDirectory, GeneratedSourcesDirectoryExistsStrategy.RUN);
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
            final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, JSpecifyAnnotationType.NULL_MARKED, tmpSourceDirectory, tmpTargetDirectory, GeneratedSourcesDirectoryExistsStrategy.RUN);
            final Path javaPackage = tmpSourceDirectory.resolve("src/main/java/foo/bar");

            filesMockedStatic.when(() -> Files.createDirectories(any(Path.class)))
                             .thenThrow(new AccessDeniedException("access denied"));

            // Act & Assert
            Assertions.assertThatCode(() -> PackageInfoFileWriter.createPackageInfo(context, javaPackage))
                      .isInstanceOf(CantCreatePackageInfoException.class)
                      .hasMessage("Failed to write package-info.java files");
        }
    }

    @Test
    void should_not_write_when_package_info_file_already_exists() throws IOException {
        // Arrange
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(null, mockLog, false, JSpecifyAnnotationType.NULL_MARKED, tmpSourceDirectory, tmpTargetDirectory, GeneratedSourcesDirectoryExistsStrategy.RUN);
        final Path javaPackage = tmpSourceDirectory.resolve("src/main/java/foo/bar");
        final Path targetDirectory = tmpTargetDirectory.resolve("src/main/java/foo/bar");
        Files.createDirectories(targetDirectory);
        Files.createFile(targetDirectory.resolve("package-info.java"));

        // Act
        PackageInfoFileWriter.createPackageInfo(context, javaPackage);

        // Assert
        verify(mockLog).info(ArgumentMatchers.startsWith("Ignore generation for file"));
    }

}
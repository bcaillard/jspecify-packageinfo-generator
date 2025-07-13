package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
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
            PackageInfoGeneratorEngine.generate(context);

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
            PackageInfoGeneratorEngine.generate(context);

            // Assert
            filesMockedStatic.verify(() -> Files.walkFileTree(any(Path.class), any(JavaPackageVisitor.class)), never());
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
            PackageInfoGeneratorEngine.generate(context);

            // Assert
            filesMockedStatic.verify(() -> Files.walkFileTree(any(Path.class), any(JavaPackageVisitor.class)));
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
            filesMockedStatic.when(() -> Files.walkFileTree(any(Path.class), any(JavaPackageVisitor.class)))
                             .thenThrow(new IllegalArgumentException("Test exception"));

            // Act & Assert
            Assertions.assertThatCode(() -> PackageInfoGeneratorEngine.generate(context))
                      .isInstanceOf(MojoExecutionException.class)
                      .hasMessage("Failed to generate package-info.java files. Error occurred during files generation process");
        }
    }

}
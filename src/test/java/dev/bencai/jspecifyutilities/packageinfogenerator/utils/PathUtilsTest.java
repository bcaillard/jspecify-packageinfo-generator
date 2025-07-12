package dev.bencai.jspecifyutilities.packageinfogenerator.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for {@link PathUtils}. */
class PathUtilsTest {

    /** Tests for {@link PathUtils#isPackageInfoFileExists(Path)}. */
    @Nested
    class IsPackageInfoFileExists {

        @Test
        void should_return_false_when_directory_does_not_exist() {
            // Arrange
            final Path nonExistentDir = Path.of("non-existent-dir");

            // Act
            final boolean actual = PathUtils.isPackageInfoFileExists(nonExistentDir);

            // Assert
            assertFalse(actual, "Expected package-info.java to not exist in a non-existent directory.");
        }

        @Test
        void should_return_true_when_package_info_file_exists() throws IOException {
            // Arrange
            final Path tempDir = Files.createTempDirectory("testDir");
            final Path packageInfoFile = tempDir.resolve("package-info.java");
            Files.createFile(packageInfoFile);

            // Act
            final boolean actual = PathUtils.isPackageInfoFileExists(tempDir);

            // Assert
            assertTrue(actual, "Expected package-info.java to exist, but it was not found.");

            // Cleanup
            Files.deleteIfExists(packageInfoFile);
            Files.deleteIfExists(tempDir);
        }

        @Test
        void should_return_false_when_package_info_file_does_not_exist() throws IOException {
            // Arrange
            final Path tempDir = Files.createTempDirectory("testDir");

            // Act
            final boolean actual = PathUtils.isPackageInfoFileExists(tempDir);

            // Assert
            assertFalse(actual, "Expected package-info.java to not exist, but it was found.");

            // Cleanup
            Files.deleteIfExists(tempDir);
        }

    }

    /** Tests for {@link PathUtils#hasJavaSourceFiles(Path)}. */
    @Nested
    class HasJavaSourceFiles {

        @Test
        void should_return_true_when_java_source_files_exist() throws IOException {
            // Arrange
            final Path tempDir = Files.createTempDirectory("testDirWithJavaFiles");
            final Path javaFile = tempDir.resolve("TestFile.java");
            Files.createFile(javaFile);

            // Act
            final boolean actual = PathUtils.hasJavaSourceFiles(tempDir);

            // Assert
            assertThat(actual).isTrue();

            // Cleanup
            Files.deleteIfExists(javaFile);
            Files.deleteIfExists(tempDir);
        }

        @Test
        void should_return_false_when_no_java_source_files_exist() throws IOException {
            // Arrange
            final Path tempDir = Files.createTempDirectory("testDirWithoutJavaFiles");
            final Path nonJavaFile = tempDir.resolve("example.txt");
            Files.createFile(nonJavaFile);

            // Act
            final boolean actual = PathUtils.hasJavaSourceFiles(tempDir);

            // Assert
            assertThat(actual).isFalse();

            // Cleanup
            Files.deleteIfExists(nonJavaFile);
            Files.deleteIfExists(tempDir);
        }

        @Test
        void should_return_false_when_directory_is_empty() throws IOException {
            // Arrange
            final Path tempDir = Files.createTempDirectory("emptyTestDir");

            // Act
            final boolean actual = PathUtils.hasJavaSourceFiles(tempDir);

            // Assert
            assertThat(actual).isFalse();

            // Cleanup
            Files.deleteIfExists(tempDir);
        }

    }

}
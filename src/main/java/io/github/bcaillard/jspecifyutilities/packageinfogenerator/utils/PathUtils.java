package io.github.bcaillard.jspecifyutilities.packageinfogenerator.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.JAVA_EXTENSION;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.PACKAGE_INFO_FILENAME;

/**
 * Utility class providing methods for working with file paths.
 * Designed for tasks such as checking the existence of specific files
 * and identifying directories containing Java source code.
 */
@UtilityClass
public class PathUtils {

    /**
     * Checks if the "package-info.java" file exists in the given directory path.
     *
     * @param path the directory path to check for the existence of the "package-info.java" file
     *
     * @return {@code true} if the "package-info.java" file exists in the specified directory, {@code false} otherwise
     */
    public static boolean isPackageInfoFileExists(final Path path) {
        final Path packageInfoPath = path.resolve(PACKAGE_INFO_FILENAME);
        return Files.exists(packageInfoPath);
    }

    /**
     * Checks if the specified directory contains any Java source files.
     * A Java source file is identified by a file name ending with the ".java" extension.
     *
     * @param path the path to the directory to scan for Java source files
     *
     * @return {@code true} if the directory contains at least one Java source file, {@code false} otherwise
     *
     * @throws IOException if an I/O error occurs while accessing the directory
     */
    public static boolean hasJavaSourceFiles(final Path path) throws IOException {
        try (final Stream<Path> paths = Files.list(path)) {
            return paths.anyMatch(file -> file.toString().endsWith(JAVA_EXTENSION));
        }
    }

    /**
     * Checks if the specified directory is empty (contains no files or subdirectories).
     *
     * @param path the path to the directory to check
     *
     * @return {@code true} if the directory is empty, {@code false} otherwise
     *
     * @throws IOException if an I/O error occurs while accessing the directory
     */
    public static boolean isEmptyFolder(final Path path) throws IOException {
        try (final Stream<Path> entries = Files.list(path)) {
            return entries.findAny().isEmpty();
        }
    }

}
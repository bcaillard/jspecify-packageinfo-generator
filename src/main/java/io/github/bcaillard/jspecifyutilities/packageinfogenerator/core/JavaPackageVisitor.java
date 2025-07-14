package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.utils.PathUtils;
import lombok.RequiredArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

/**
 * File visitor that traverses directories to identify Java packages that need {@code package-info.java} files.
 * It skips directories that already have {@code package-info.java} or don't contain any Java source files.
 */
@RequiredArgsConstructor
public class JavaPackageVisitor extends SimpleFileVisitor<Path> {

    /** The logging instance provided by the Maven plugin. */
    private final Log log;
    /** Consumer that processes directories requiring {@code package-info.java} generation. */
    private final Consumer<Path> consumer;

    /**
     * Visits a directory before visiting its contents to determine if it needs {@code package-info.java} generation.
     *
     * @param dir the directory to visit
     * @param attrs the directory's basic attributes
     *
     * @return CONTINUE to continue visiting subsequent files and directories
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        log.debug(String.format("Visiting directory: %s", dir));

        if (!shouldSkipDirectory(dir)) {
            processDirectory(dir);
        }

        return FileVisitResult.CONTINUE;
    }

    /**
     * Determines if a directory should be skipped from {@code package-info.java} generation.
     *
     * @param dir the directory to check
     *
     * @return true if the directory should be skipped, false otherwise
     *
     * @throws IOException if an I/O error occurs while checking the directory
     */
    private boolean shouldSkipDirectory(final Path dir) throws IOException {
        boolean skipDirectory = false;

        if (PathUtils.isPackageInfoFileExists(dir)) {
            log.debug(String.format("Package Info already exists. Skipping directory: %s", dir));
            skipDirectory = true;
        } else if (!PathUtils.hasJavaSourceFiles(dir)) {
            log.debug(String.format("No Java files. Skipping directory: %s", dir));
            skipDirectory = true;
        }

        return skipDirectory;
    }

    /**
     * Processes a directory that requires {@code package-info.java} generation.
     *
     * @param dir the directory to process
     */
    private void processDirectory(final Path dir) {
        log.debug(String.format("Directory eligible for package-info.java creation: %s", dir));
        consumer.accept(dir);
    }

}
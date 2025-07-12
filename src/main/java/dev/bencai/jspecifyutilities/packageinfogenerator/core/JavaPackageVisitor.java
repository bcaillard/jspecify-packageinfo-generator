package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import dev.bencai.jspecifyutilities.packageinfogenerator.utils.PathUtils;
import lombok.RequiredArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class JavaPackageVisitor extends SimpleFileVisitor<Path> {

    private final Log log;
    private final Consumer<Path> consumer;

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        log.debug(String.format("Visiting directory: %s", dir));

        if (!shouldSkipDirectory(dir)) {
            processDirectory(dir);
        }

        return FileVisitResult.CONTINUE;
    }

    private boolean shouldSkipDirectory(final Path dir) throws IOException {
        boolean skipDirectory = false;

        if (PathUtils.isPackageInfoFileExists(dir)) {
            log.info(String.format("Package Info already exists. Skipping directory: %s", dir));
            skipDirectory = true;
        } else if (!PathUtils.hasJavaSourceFiles(dir)) {
            log.info(String.format("No Java files. Skipping directory: %s", dir));
            skipDirectory = true;
        }

        return skipDirectory;
    }

    private void processDirectory(final Path dir) {
        log.debug(String.format("Directory eligible for package-info.java creation: %s", dir));
        consumer.accept(dir);
    }

}
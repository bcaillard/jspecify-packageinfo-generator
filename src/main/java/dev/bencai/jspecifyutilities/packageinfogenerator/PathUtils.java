package dev.bencai.jspecifyutilities.packageinfogenerator;

import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PathUtils {

    public static void listFilesRecursively(final Path directory, final Log log, final Consumer<Path> consumer) throws IOException {

        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
                boolean skip = false;
                log.info("Directory: " + dir);
                final Path packageInfoFile = dir.resolve("package-info.java");
                if (Files.exists(packageInfoFile)) {
                    log.info("Skipping directory package-info: " + dir);
                    skip = true;
                }
                if (!hasJavaSourceFiles(dir)) {
                    log.info("Skipping directory empty java: " + dir);
                    skip = true;
                }

                if (!skip) {
                    log.error("!!!!! " + dir);
                    final String suffixe = dir.toString().replace(directory.toString(), "").replaceFirst("/", "");
                    final Path path = Paths.get(suffixe);
                    consumer.accept(path);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static boolean hasJavaSourceFiles(final Path path) throws IOException {
        try (final Stream<Path> paths = Files.list(path)) {
            return paths.anyMatch(file -> file.toString().endsWith(".java"));
        }
    }

}
package dev.bencai.jspecifyutilities.packageinfogenerator;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.JAVA_EXTENSION;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.PACKAGE_INFO_FILENAME;

@UtilityClass
public class PathUtils {

    public static boolean isPackageInfoFileExists(final Path path) {
        final Path packageInfoPath = path.resolve(PACKAGE_INFO_FILENAME);
        return Files.exists(packageInfoPath);
    }

    public static boolean hasJavaSourceFiles(final Path path) throws IOException {
        try (final Stream<Path> paths = Files.list(path)) {
            return paths.anyMatch(file -> file.toString().endsWith(JAVA_EXTENSION));
        }
    }

}
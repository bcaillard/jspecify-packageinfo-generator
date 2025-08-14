package io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.PACKAGE_INFO_FILENAME;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.PACKAGE_SEPARATOR;

/**
 * Utility class responsible for creating and writing {@code package-info.java} files for specified Java packages.
 * This class aims to facilitate the automatic generation of annotated {@code package-info.java} files during build processes.
 */
@UtilityClass
public class PackageInfoFileWriter {

    /**
     * Creates a {@code package-info.java} file for a specified Java package and writes it to the appropriate directory in the generated sources.
     *
     * @param context the context containing configuration and resources needed for package info generation, such as source and output directories, annotations, and logging utilities
     * @param fromJavaPackage the path representing the full directory of the Java package for which the {@code package-info.java} file will be created
     *
     * @throws CantCreatePackageInfoException if an exception occurs during the creation or writing of the {@code package-info.java}
     * file
     */
    public static void createPackageInfo(final PackageInfoGeneratorContext context, final Path fromJavaPackage) {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();

        logger.debug("Java package fullpath: " + fromJavaPackage);
        final String sourceDirectoryTrailingFileSeparator = sourcesDirectory + File.separator;
        final String packagePath = fromJavaPackage.toString().replace(sourceDirectoryTrailingFileSeparator, "");
        logger.debug("Java package path only: " + packagePath);

        final Path packageInfoFolder = context.getGeneratedSourcesDirectory().resolve(Paths.get(packagePath));
        logger.debug("Generated directory: " + packageInfoFolder);
        final Path packageInfoFile = packageInfoFolder.resolve(PACKAGE_INFO_FILENAME);
        logger.debug("Generated Package Info: " + packageInfoFile);

        // Détermine le nom du package à partir du chemin source
        final String packageNameOnly = packagePath.replace(File.separatorChar, PACKAGE_SEPARATOR);
        logger.debug("Java package of Package Info: " + packageNameOnly);

        // Écriture du contenu dans package-info.java
        try {
            Files.createDirectories(packageInfoFolder);
            logger.debug(String.format("Directories created: %s", packageInfoFolder));

            Files.createFile(packageInfoFile);
            logger.debug(String.format("Empty package-info.java created: %s", packageInfoFile));

            Files.write(packageInfoFile, PackageInfoTemplateFileProvider.provideContent(context.getAnnotation().getAnnotationName(), packageNameOnly));
            logger.info(String.format("Created package-info.java with @%s annotation in package: %s", context.getAnnotation().getAnnotationName(), packageNameOnly));
        } catch (final FileAlreadyExistsException faee) {
// TODO skip silently
            logger.warn(faee.getMessage());
        } catch (final IOException ioe) {
            logger.error(String.format("Failed to write package-info.java files: %s", ioe.getMessage()));
            throw new CantCreatePackageInfoException("Failed to write package-info.java files", ioe);
        }
    }

}
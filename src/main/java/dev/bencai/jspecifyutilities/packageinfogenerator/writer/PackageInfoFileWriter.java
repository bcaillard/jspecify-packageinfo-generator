package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.PACKAGE_INFO_FILENAME;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.PACKAGE_SEPARATOR;

@UtilityClass
public class PackageInfoFileWriter {

    public static void createPackageInfo(final PackageInfoGeneratorContext context, final Path sourceDirectory) {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();

        logger.debug("Java package fullpath: " + sourceDirectory);
        final String packagePath = sourceDirectory.toString()
                                                  .replace(sourcesDirectory.toString(), "")
                                                  .replaceFirst(File.separator, "");
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
        } catch (final IOException ioe) {
            throw new CantCreatePackageInfoException("Failed to write package-info.java files", ioe);
        }
    }

}
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.abc.GeneratedHandler;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.PackageInfoFileWriter;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/** Class responsible for generating {@code package-info.java} files in a Java project. */
@UtilityClass
public class PackageInfoGeneratorEngine {

    /**
     * Generates {@code package-info.java} files for the specified source directory in the given context.
     *
     * @param context the context containing configuration and resources required for the generation of
     * {@code package-info.java} files, including the sources directory, logging mechanism,
     * and annotations.
     *
     * @throws MojoExecutionException if an error occurs during the generation process, such as failure
     * to walk through the source directory or create the package information files.
     */
    public static void generate(final PackageInfoGeneratorContext context) throws MojoExecutionException, IOException {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();
        final Path generatedSourcesDirectory = context.getGeneratedSourcesDirectory();

        if (context.isSkip()) {
            logger.info("Skipping package-info.java generation because property 'skip' is set to true");
            // skip execption
        } else if (!Files.exists(sourcesDirectory)) {
            logger.info("Skipping package-info.java generation because there is no sources directory");
            // skip execption
        } else {
            if (Files.exists(generatedSourcesDirectory)) {
                try (final Stream<Path> entries = Files.list(generatedSourcesDirectory)) {
                    final boolean b = entries.findFirst().isPresent();
                }
                GeneratedHandler.handler(null, context.getGeneratedSourcesDirectory());
            }
        }

        logger.debug("Generating package-info.java files");

        try {
            final JavaPackageVisitor javaPackageVisitor = new JavaPackageVisitor(logger, p -> PackageInfoFileWriter.createPackageInfo(context, p));
            Files.walkFileTree(sourcesDirectory, javaPackageVisitor);
            logger.debug("All package-info.java files have been generated");
        } catch (
                final Exception ex) {
            throw new MojoExecutionException("Failed to generate package-info.java files. Error occurred during files generation process", ex);
        }
    }

}
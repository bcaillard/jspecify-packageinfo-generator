package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import dev.bencai.jspecifyutilities.packageinfogenerator.writer.PackageInfoFileWriter;
import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class PackageInfoGeneratorEngine {

    public static void generate(final PackageInfoGeneratorContext context) throws MojoExecutionException {
        final Log logger = context.getLog();
        final Path sourcesDirectory = context.getSourcesDirectory();

        if (context.isSkip()) {
            logger.info("Skipping package-info.java generation because property 'skip' is set to true");
        } else if (!Files.exists(sourcesDirectory)) {
            logger.info("Skipping package-info.java generation because there is no sources directory");
        } else {
            logger.debug("Generating package-info.java files");
            try {
                final JavaPackageVisitor javaPackageVisitor = new JavaPackageVisitor(logger, p -> PackageInfoFileWriter.createPackageInfo(context, p));
                Files.walkFileTree(sourcesDirectory, javaPackageVisitor);
                logger.debug("All package-info.java files have been generated");
            } catch (final Exception ex) {
                throw new MojoExecutionException("Failed to generate package-info.java files. Error occurred during files generation process", ex);
            }
        }
    }

}
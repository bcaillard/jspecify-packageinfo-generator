package dev.bencai.jspecifyutilities.packageinfogenerator.configuration;

import lombok.Data;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;

/**
 * Context class for generating {@code package-info.java} files during the Maven plugin execution process.
 * <p>This class encapsulates the configuration and resources required to facilitate the generation of annotated {@code package-info.java} files.</p>
 */
@Data
public class PackageInfoGeneratorContext {

    /** The MavenProject instance representing the current Maven project. */
    private final MavenProject project;
    /** The logging instance provided by the Maven plugin. */
    private final Log log;
    /** A flag indicating whether the generation process should be skipped. */
    private final boolean skip;
    /** The JSpecifyAnnotationType specifying the null-safety annotation type. */
    private final JSpecifyAnnotationType annotation;
    /** The path to the source directory of the Java files. */
    private final Path sourcesDirectory;
    /** The path to the directory where the generated {@code {@code package-info.java}} files will be stored. */
    private final Path generatedSourcesDirectory;

}
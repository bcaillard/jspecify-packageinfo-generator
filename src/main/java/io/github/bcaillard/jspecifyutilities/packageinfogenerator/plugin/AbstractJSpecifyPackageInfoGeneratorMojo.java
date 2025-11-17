package io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources.GeneratedSourcesDirectoryExistsStrategy;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/** Abstract Maven plugin base class for generating {@code package-info.java} files with JSpecify annotation. */
public abstract class AbstractJSpecifyPackageInfoGeneratorMojo extends AbstractMojo {

    /** Represents the Maven project. */
    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;
    /** Whether to skip the execution of this plugin. */
    @Parameter(property = "skip", defaultValue = "false")
    protected boolean skip;
    /** The type of JSpecify annotation to be added to the generated {@code package-info.java} files. */
    @Parameter(property = "annotation", defaultValue = "NULL_MARKED")
    protected JSpecifyAnnotationType annotation;
    /** Strategy to apply when the generated sources directory already exists. */
    @Parameter(property = "whenGeneratedSourcesDirectoryExists", defaultValue = "SKIP")
    protected GeneratedSourcesDirectoryExistsStrategy whenGeneratedSourcesDirectoryExists;

    /**
     * Detects whether at least one generated {@code package-info.java} file exists under the provided
     * generated sources directory.
     * Subclasses are responsible for adding the directory to the appropriate source roots and logging.
     *
     * @param generatedSourcesDirectory Path to the generated sources root directory
     * @return true if a {@code package-info.java} file exists, false otherwise
     */
    protected boolean hasGeneratedPackageInfoFiles(final Path generatedSourcesDirectory) {
        try {
            if (generatedSourcesDirectory == null || !Files.exists(generatedSourcesDirectory)) {
                return false;
            }
            try (Stream<Path> paths = Files.walk(generatedSourcesDirectory)) {
                return paths.anyMatch(p -> p.getFileName() != null && "package-info.java".equals(p.getFileName().toString()));
            }
        } catch (final RuntimeException | IOException ex) {
            getLog().warn("Failed to inspect generated sources directory for package-info.java files ", ex);
            return false;
        }
    }
}
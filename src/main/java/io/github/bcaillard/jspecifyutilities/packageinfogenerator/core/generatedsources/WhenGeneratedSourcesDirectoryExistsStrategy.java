package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;

/**
 * Defines a strategy to be executed when the generated sources directory already exists.
 * Implementations of this interface determine how to handle the case where the directory
 * is present, such as failing, skipping the process, or continuing regardless.
 */
public interface WhenGeneratedSourcesDirectoryExistsStrategy {

    /**
     * Executes a specific action based on the existence of the generated sources directory.
     *
     * @param generatedSourcesDirectoryExistsStrategy path to the generated sources directory that already exists.
     * @param logger The logging instance provided by the Maven plugin
     *
     * @throws MojoExecutionException if the applied strategy results in an exception.
     */
    void whenExists(Path generatedSourcesDirectoryExistsStrategy, Log logger) throws MojoExecutionException;

}
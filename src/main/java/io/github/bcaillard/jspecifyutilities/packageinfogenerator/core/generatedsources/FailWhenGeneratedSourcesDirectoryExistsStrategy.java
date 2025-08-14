package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;

/** Strategy causing the process to fail when the generated sources directory already exists. */
public class FailWhenGeneratedSourcesDirectoryExistsStrategy implements WhenGeneratedSourcesDirectoryExistsStrategy {

    @Override
    public void whenExists(final Path generatedSourcesDirectoryExistsStrategy, final Log logger) throws MojoExecutionException {
        throw new MojoExecutionException("Failed because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=FAIL'");
    }

}
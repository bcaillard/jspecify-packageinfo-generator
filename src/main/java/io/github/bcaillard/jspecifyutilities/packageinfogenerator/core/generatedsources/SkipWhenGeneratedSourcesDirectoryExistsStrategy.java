package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.SkipProcessException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;

/** Strategy ignoring processing when the generated sources directory already exists. */
public class SkipWhenGeneratedSourcesDirectoryExistsStrategy implements WhenGeneratedSourcesDirectoryExistsStrategy {

    @Override
    public void whenExists(final Path generatedSourcesDirectoryExistsStrategy, final Log logger) {
        throw new SkipProcessException("Skipping package-info.java generation because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=SKIP'");
    }

}
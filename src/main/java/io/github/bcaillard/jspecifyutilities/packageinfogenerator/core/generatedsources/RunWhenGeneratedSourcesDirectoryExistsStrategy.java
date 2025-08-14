package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;

/** Strategy applying the process even though the generated sources directory already exists. */
public class RunWhenGeneratedSourcesDirectoryExistsStrategy implements WhenGeneratedSourcesDirectoryExistsStrategy {

    @Override
    public void whenExists(final Path generatedSourcesDirectoryExistsStrategy, final Log logger) {
        logger.debug("The process continues despite the existence of the generated sources directory, because 'whenGeneratedSourcesDirectoryExists=RUN'");
    }

}
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;
import java.util.EnumMap;

/**
 * <p>Utility class responsible for handling the existence of the generated sources directory based on the specified {@link GeneratedSourcesDirectoryExistsStrategy}.</p>
 * Available strategies include:<br>
 * <ul>
 * <li><code>SKIP</code>: The operation is skipped if the directory exists.</li>
 * <li><code>FAIL</code>: An exception is thrown if the directory exists.</li>
 * <li><code>RUN</code>: The operation continues regardless of whether the directory exists.</li>
 * </ul>
 */
@UtilityClass
public class GeneratedSourcesDirectoryExistsHandler {

    static final EnumMap<GeneratedSourcesDirectoryExistsStrategy, WhenGeneratedSourcesDirectoryExistsStrategy> strategies = new EnumMap<>(GeneratedSourcesDirectoryExistsStrategy.class);

    static {
        strategies.put(GeneratedSourcesDirectoryExistsStrategy.SKIP, new SkipWhenGeneratedSourcesDirectoryExistsStrategy());
        strategies.put(GeneratedSourcesDirectoryExistsStrategy.FAIL, new FailWhenGeneratedSourcesDirectoryExistsStrategy());
        strategies.put(GeneratedSourcesDirectoryExistsStrategy.RUN, new RunWhenGeneratedSourcesDirectoryExistsStrategy());
    }

    /**
     * Handles the existence of the generated sources directory based on the provided strategy.
     *
     * @param generatedSourcesDirectoryExistsStrategy strategy to apply when the generated sources directory exists.
     * @param generatedSourcesDirectory path to the generated sources directory to which the strategy will be applied.
     * @param logger logging instance provided by the Maven plugin
     *
     * @throws MojoExecutionException if the applied strategy results in an exception.
     */
    public static void handle(final GeneratedSourcesDirectoryExistsStrategy generatedSourcesDirectoryExistsStrategy, final Path generatedSourcesDirectory, final Log logger) throws MojoExecutionException {
        final WhenGeneratedSourcesDirectoryExistsStrategy strategyToApply = strategies.get(generatedSourcesDirectoryExistsStrategy);
        strategyToApply.whenExists(generatedSourcesDirectory, logger);
    }

}
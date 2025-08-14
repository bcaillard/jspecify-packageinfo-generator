package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

/**
 * Enumeration of strategies to apply when the generated sources directory already exists.
 * <ul>
 * <li><code>SKIP</code>: The operation is skipped if the directory exists.</li>
 * <li><code>FAIL</code>: An exception is thrown if the directory exists.</li>
 * <li><code>RUN</code>: The operation continues regardless of whether the directory exists.</li>
 * </ul>
 */
public enum GeneratedSourcesDirectoryExistsStrategy {
    SKIP,
    FAIL,
    RUN
}
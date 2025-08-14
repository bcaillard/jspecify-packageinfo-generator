package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core;

/** Exception thrown to indicate that the generation process should not be processed. */
public class SkipProcessException extends RuntimeException {

    public SkipProcessException(final String message) {
        super(message);
    }

}
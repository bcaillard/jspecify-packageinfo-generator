package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import java.io.IOException;

/**
 * Exception thrown when the creation of a {@code package-info.java} file fails.
 * <p>
 * This exception is used to wrap {@link IOException} instances that occur during the process of generating or writing a {@code package-info.java} file, providing additional context about the
 * failure.<p>
 */
public class CantCreatePackageInfoException extends RuntimeException {

    public CantCreatePackageInfoException(final String message, final IOException ioe) {
        super(message, ioe);
    }

}
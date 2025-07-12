package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import java.io.IOException;

public class CantCreatePackageInfoException extends RuntimeException {

    public CantCreatePackageInfoException(final String message, final IOException ioe) {
        super(message, ioe);
    }

}
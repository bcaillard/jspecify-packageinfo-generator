package io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * Utility class responsible for providing content for "package-info.java" files.
 * The generated content includes a JSpecify annotation and the package declaration.
 */
@UtilityClass
public class PackageInfoTemplateFileProvider {

    /**
     * Generates the content for a "package-info.java" file with a specified annotation and package declaration.
     *
     * @param annotationName the name of the JSpecify annotation to include in the file
     * @param packageNameOnly the name of the package for the package declaration
     *
     * @return a byte array containing the generated content encoded in UTF-8
     */
    public static byte[] provideContent(final String annotationName, final String packageNameOnly) {
        return new StringJoiner(System.lineSeparator())
                .add(String.format("@org.jspecify.annotations.%s", annotationName))
                .add(String.format("package %s;", packageNameOnly))
                .toString()
                .getBytes(StandardCharsets.UTF_8);
    }

}
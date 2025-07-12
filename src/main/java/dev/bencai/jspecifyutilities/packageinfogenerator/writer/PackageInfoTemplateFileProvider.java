package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@UtilityClass
public class PackageInfoTemplateFileProvider {

    public static byte[] provideContent(final String annotationName, final String packageNameOnly) {
        return new StringJoiner(System.lineSeparator())
                .add(String.format("@org.jspecify.annotations.%s", annotationName))
                .add(String.format("package %s;", packageNameOnly))
                .toString()
                .getBytes(StandardCharsets.UTF_8);
    }

}
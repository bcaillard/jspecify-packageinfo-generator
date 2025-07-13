package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import lombok.experimental.UtilityClass;

import java.io.File;

/** Contains constant values used throughout the Maven plugin execution process. */
@UtilityClass
public class PackageInfoGeneratorConstants {

    public static final String PACKAGE_INFO_FILENAME = "package-info.java";
    public static final String MAIN_JAVA_DIRECTORY = "src" + File.separator + "main" + File.separator + "java";
    public static final String TEST_JAVA_DIRECTORY = "src" + File.separator + "test" + File.separator + "java";
    public static final String MAIN_OUTPUT_DIRECTORY = "generated-sources/annotations/packages-info";
    public static final String TEST_OUTPUT_DIRECTORY = "generated-test-sources/test-annotations/packages-info";
    public static final String JAVA_EXTENSION = ".java";
    public static final char PACKAGE_SEPARATOR = '.';

}
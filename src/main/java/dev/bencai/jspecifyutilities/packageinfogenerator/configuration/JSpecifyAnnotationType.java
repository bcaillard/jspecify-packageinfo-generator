package dev.bencai.jspecifyutilities.packageinfogenerator.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Represents the different types of JSpecify annotations applicable on each {@code package-info.java} generated. */
@Getter
@RequiredArgsConstructor
public enum JSpecifyAnnotationType {
    /** Represents explicitly annotated null-safety with JSpecify annotations. */
    NULL_MARKED("NullMarked"),
    /** Represents the absence of explicit null-safety annotation in the code. */
    NULL_UNMARKED("NullUnmarked");
    /** Name of the JSpecify annotation. */
    private final String annotationName;
}
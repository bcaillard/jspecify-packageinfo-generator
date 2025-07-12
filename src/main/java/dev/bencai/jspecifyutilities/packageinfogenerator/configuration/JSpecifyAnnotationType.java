package dev.bencai.jspecifyutilities.packageinfogenerator.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JSpecifyAnnotationType {
    NULL_MARKED("NullMarked"),
    NULL_UNMARKED("NullUnmarked");
    private final String annotationName;
}
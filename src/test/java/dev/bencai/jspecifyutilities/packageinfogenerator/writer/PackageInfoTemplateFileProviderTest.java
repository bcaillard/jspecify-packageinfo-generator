package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests for {@link PackageInfoTemplateFileProvider}. */
class PackageInfoTemplateFileProviderTest {

    @Test
    void should_return_correct_content_when_valid_annotationName_and_packageNameOnly_provided() {
        final String annotationName = JSpecifyAnnotationType.NULL_UNMARKED.getAnnotationName();
        final String packageNameOnly = "com.example.test";

        final byte[] actual = PackageInfoTemplateFileProvider.provideContent(annotationName, packageNameOnly);

        assertThat(new String(actual, StandardCharsets.UTF_8).split(System.lineSeparator()))
                .containsExactly(
                        "@org.jspecify.annotations.NullUnmarked",
                        "package com.example.test;"
                );
    }

}
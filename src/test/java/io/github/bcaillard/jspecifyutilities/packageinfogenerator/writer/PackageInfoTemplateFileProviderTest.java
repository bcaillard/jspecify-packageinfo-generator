/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests for {@link io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.PackageInfoTemplateFileProvider}. */
class PackageInfoTemplateFileProviderTest {

    @Test
    void should_return_correct_content_when_valid_annotationName_and_packageNameOnly_provided() {
        // Arrange
        final String annotationName = JSpecifyAnnotationType.NULL_UNMARKED.getAnnotationName();
        final String packageNameOnly = "com.example.test";

        // Act
        final byte[] actual = io.github.bcaillard.jspecifyutilities.packageinfogenerator.writer.PackageInfoTemplateFileProvider.provideContent(annotationName, packageNameOnly);

        // Assert
        assertThat(new String(actual, StandardCharsets.UTF_8).split(System.lineSeparator()))
                .containsExactly(
                        "@org.jspecify.annotations.NullUnmarked",
                        "package com.example.test;"
                );
    }

}
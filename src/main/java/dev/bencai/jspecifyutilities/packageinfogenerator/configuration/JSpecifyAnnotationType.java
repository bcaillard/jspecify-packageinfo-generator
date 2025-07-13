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
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
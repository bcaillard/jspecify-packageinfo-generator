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
package dev.bencai.jspecifyutilities.packageinfogenerator.writer;

import java.io.IOException;

/**
 * Exception thrown when the creation of a {@code package-info.java} file fails.
 * <p>
 * This exception is used to wrap {@link IOException} instances that occur during the process of generating or writing a {@code package-info.java} file, providing additional context about the
 * failure.<p>
 */
public class CantCreatePackageInfoException extends RuntimeException {

    public CantCreatePackageInfoException(final String message, final IOException ioe) {
        super(message, ioe);
    }

}
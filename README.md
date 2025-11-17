# JSpecify Package Info Generator Maven Plugin

This Maven plugin automatically generates `package-info.java` files with [JSpecify](https://jspecify.dev) annotations (`@NullMarked` and `@NullUnmarked`), helping you manage
nullness boundaries in your Java projects without manual boilerplate.

It scans your source directories, creates missing `package-info.java` files where needed, and adds either `@NullMarked` or `@NullUnmarked` based on your configuration. This allows
you to systematically adopt JSpecify nullness tracking across your codebase while maintaining a clean structure.

If you discover JSpecify, remember to take a look at [NullAway](https://github.com/uber/NullAway) to eliminate NullPointerException in your Java projects.

## Goals

- Automatically creates `package-info.java` files in packages that are missing them
- Adds `@NullMarked` or `@NullUnmarked` from JSpecify to support null-safety adoption
- Reduces repetitive manual work when migrating to JSpecify
- Automatically adds generated directories to Maven source roots so they are compiled

## Requirements

- **Java 11 or higher**
- **Maven 3+**

## Usage

Add the plugin to your `pom.xml`:

```xml

<build>
    <plugins>
        <plugin>
            <groupId>io.github.bcaillard.jspecify-utilities</groupId>
            <artifactId>jspecify-packageinfo-generator-maven-plugin</artifactId>
            <version>(pick version)</version>
            <executions>
                <execution>
                    <goals>
                        <!-- 
                            Whether you want to generate package-java.info files from src/main/java.
                            package-info.java are generated in /target/generated-sources/annotations/packages-info/ folder.
                        -->
                        <goal>generate-package-info</goal>
                        <!-- 
                            Whether you want to generate package-java.info files from src/test/java. 
                            package-info.java are generated in /target/generated-test-sources/test-annotations/packages-info/ folder.
                        -->
                        <goal>generate-package-info-test</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <!-- Whether you want skip execution (optional, default is false) -->
                <skip>false</skip>
                <!-- JSpecify annotation to be added to each package-info.java. Choose between NULL_MARKED and NULL_UNMARKED (optional, default is NULL_MARKED) -->
                <annotation>NULL_UNMARKED</annotation>
                <!-- Strategy to apply when the generated sources directory already exists [RUN, FAIL, SKIP] (optional, default is SKIP) -->
                <whenGeneratedSourcesDirectoryExists>RUN</whenGeneratedSourcesDirectoryExists>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Be careful with Maven Compiler Plugin

Since version `3.12.0`, there's a regression that makes it impossible to use generated-sources.
See [MCOMPILER-538](https://github.com/apache/maven-compiler-plugin/pull/191).

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
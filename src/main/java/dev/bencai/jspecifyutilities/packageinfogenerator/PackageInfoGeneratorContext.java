package dev.bencai.jspecifyutilities.packageinfogenerator;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;

/** Représentation du contexte du plugin avec ses propriétés, le logger Maven, etc. */
@Data
public class PackageInfoGeneratorContext {

    private final MavenProject project;
    private final Log log;
    private final boolean skip;
    private final JSpecifyAnnotationType annotation;
    private final Path sourcesDirectory;
    private final Path generatedSourcesDirectory;

}
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources.GeneratedSourcesDirectoryExistsStrategy;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/** Abstract Maven plugin base class for generating {@code package-info.java} files with JSpecify annotation. */
public abstract class AbstractJSpecifyPackageInfoGeneratorMojo extends AbstractMojo {

    /** Represents the Maven project. */
    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;
    /** Whether to skip the execution of this plugin. */
    @Parameter(property = "skip", defaultValue = "false")
    protected boolean skip;
    /** The type of JSpecify annotation to be added to the generated {@code package-info.java} files. */
    @Parameter(property = "annotation", defaultValue = "NULL_MARKED")
    protected JSpecifyAnnotationType annotation;
    /** Strategy to apply when the generated sources directory already exists. */
    @Parameter(property = "whenGeneratedSourcesDirectoryExists", defaultValue = "SKIP")
    protected GeneratedSourcesDirectoryExistsStrategy whenGeneratedSourcesDirectoryExists;
    /** Whether to add the generated sources as compile source root. */
    @Parameter(property = "addToSourceRoot", defaultValue = "true")
    protected boolean addGeneratedSourcesToSourceRoot;

}
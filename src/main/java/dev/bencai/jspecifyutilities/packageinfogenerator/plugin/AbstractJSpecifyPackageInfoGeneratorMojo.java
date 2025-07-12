package dev.bencai.jspecifyutilities.packageinfogenerator.plugin;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

public abstract class AbstractJSpecifyPackageInfoGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;
    @Parameter(property = "skip", defaultValue = "false")
    protected boolean skip;
    /**
     * The annotation to apply: @NullMarked or @NullUnmarked.
     */
    @Parameter(property = "annotation", defaultValue = "NULL_MARKED")
    protected JSpecifyAnnotationType annotation;

}
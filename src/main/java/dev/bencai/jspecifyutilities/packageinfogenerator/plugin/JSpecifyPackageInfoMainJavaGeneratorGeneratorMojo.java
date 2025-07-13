package dev.bencai.jspecifyutilities.packageinfogenerator.plugin;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import dev.bencai.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.MAIN_JAVA_DIRECTORY;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.MAIN_OUTPUT_DIRECTORY;

/**
 * Mojo to generate or update package-info.java files
 * with @NullMarked or @NullUnmarked from JSpecify.
 */
@Mojo(name = "generate-package-info", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo extends AbstractJSpecifyPackageInfoGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException {
        final Path sourceDirectory = Paths.get(project.getBasedir().getPath(), MAIN_JAVA_DIRECTORY);
        final Path generatedSourcesDirectory = Paths.get(project.getBuild().getDirectory(), MAIN_OUTPUT_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(project, getLog(), skip, annotation, sourceDirectory, generatedSourcesDirectory);

        PackageInfoGeneratorEngine.generate(context);
    }

}
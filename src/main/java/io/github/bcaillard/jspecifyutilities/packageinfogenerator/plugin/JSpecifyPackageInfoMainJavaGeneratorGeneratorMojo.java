package io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_JAVA_DIRECTORY;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.MAIN_OUTPUT_DIRECTORY;

/** Maven plugin for generating {@code package-info.java} files with JSpecify annotation from java main source directory. */
@Mojo(name = "generate-package-info", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo extends AbstractJSpecifyPackageInfoGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException {
        final Path sourceDirectory = Paths.get(project.getBasedir().getPath(), MAIN_JAVA_DIRECTORY);
        final Path generatedSourcesDirectory = Paths.get(project.getBuild().getDirectory(), MAIN_OUTPUT_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(
                project,
                getLog(),
                skip,
                annotation,
                sourceDirectory,
                generatedSourcesDirectory,
                whenGeneratedSourcesDirectoryExists
        );

        PackageInfoGeneratorEngine.generate(context);

        if (addGeneratedSourcesToSourceRoot) {
            project.addCompileSourceRoot(generatedSourcesDirectory.toString());
            getLog().info("Added generated sources directory to compile source roots: " + generatedSourcesDirectory);
        }
    }

}
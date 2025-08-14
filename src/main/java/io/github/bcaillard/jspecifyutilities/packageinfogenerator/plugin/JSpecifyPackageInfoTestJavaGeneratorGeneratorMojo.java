package io.github.bcaillard.jspecifyutilities.packageinfogenerator.plugin;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.configuration.PackageInfoGeneratorContext;
import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorEngine;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.TEST_JAVA_DIRECTORY;
import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.PackageInfoGeneratorConstants.TEST_OUTPUT_DIRECTORY;

/** Maven plugin for generating {@code package-info.java} files with JSpecify annotation from java test source directory. */
@Mojo(name = "generate-package-info-test", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES)
public class JSpecifyPackageInfoTestJavaGeneratorGeneratorMojo extends AbstractJSpecifyPackageInfoGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException {
        final Path testSourceDirectory = Paths.get(project.getBasedir().getPath(), TEST_JAVA_DIRECTORY);
        final Path generatedTestSourcesDirectory = Paths.get(project.getBuild().getDirectory(), TEST_OUTPUT_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(project, getLog(), skip, annotation, testSourceDirectory, generatedTestSourcesDirectory);

        PackageInfoGeneratorEngine.generate(context);
    }

}
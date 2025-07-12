package dev.bencai.jspecifyutilities.packageinfogenerator.core;

import dev.bencai.jspecifyutilities.packageinfogenerator.PackageInfoGeneratorContext;
import dev.bencai.jspecifyutilities.packageinfogenerator.PackageInfoWriter;
import dev.bencai.jspecifyutilities.packageinfogenerator.PathUtils;
import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Mojo to generate or update package-info.java files
 * with @NullMarked or @NullUnmarked from JSpecify.
 */
@Mojo(name = "generate-package-info", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GeneratePackageInfoMojo extends AbstractMojo {

    public static final String PACKAGE_INFO_FILENAME = "package-info.java";
    public static final Set<String> ALLOW_PACKAGING = Set.of("jar", "war");
    public static final String MAIN_JAVA_DIRECTORY = "src" + File.separator + "main" + File.separator + "java";
    public static final String OUTPUT_DIRECTORY = "generated-sources/annotations/packages-info";
    public static final char PACKAGE_SEPARATOR = '.';
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;
    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;
    /**
     * The annotation to apply: @NullMarked or @NullUnmarked.
     */
    @Parameter(property = "annotation", defaultValue = "NULL_MARKED")
    private JSpecifyAnnotationType annotation;

    @Override
    public void execute() throws MojoExecutionException {
        // Récupérer le dossier src/main/java
        final File sourceDirectory = new File(project.getBasedir(), MAIN_JAVA_DIRECTORY);
        final PackageInfoGeneratorContext context = new PackageInfoGeneratorContext(project, getLog(), skip, annotation, Paths.get(MAIN_JAVA_DIRECTORY), Paths.get(project.getBuild()
                                                                                                                                                                          .getDirectory(), OUTPUT_DIRECTORY));
        // try {
        try {
            PathUtils.listFilesRecursively(sourceDirectory.toPath(), getLog(), p -> {
                try {
                    PackageInfoWriter.createPackageInfo(context, p);
                    //  createPackageInfo(p.toFile(), sourceDirectory);
                } catch (final IOException e) {

                    getLog().error(e);
                }
            });
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
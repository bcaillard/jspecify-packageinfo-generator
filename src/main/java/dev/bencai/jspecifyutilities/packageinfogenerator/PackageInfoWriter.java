package dev.bencai.jspecifyutilities.packageinfogenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.GeneratePackageInfoMojo.PACKAGE_INFO_FILENAME;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.GeneratePackageInfoMojo.PACKAGE_SEPARATOR;

public class PackageInfoWriter {

    public static void createPackageInfo(final PackageInfoGeneratorContext context, final Path sourceDirectory) throws IOException {
        final Path packageInfoFolder = context.getGeneratedSourcesDirectory().resolve(sourceDirectory);
        final Path packageInfoFile = packageInfoFolder.resolve(PACKAGE_INFO_FILENAME);
        context.getLog().error("@@@@@@ " + packageInfoFile);
        // Détermine le nom du package à partir du chemin source
        final Path path = context.getProject().getBasedir().toPath();
        final Path fullMainDirectoryPath = path.resolve(context.getSourcesDirectory());
        final String packageNameOnly = sourceDirectory.toString()
                                                      .replace(fullMainDirectoryPath.toString(), "")
                                                      .replace(File.separatorChar, PACKAGE_SEPARATOR)
                                                      .replaceFirst("\\.", "");


        context.getLog().error(">>>>> " + packageNameOnly);

        // Écriture du contenu dans package-info.java
        Files.createDirectories(packageInfoFolder);
        Files.createFile(packageInfoFile);
        Files.write(packageInfoFile, List.of(
                String.format("@org.jspecify.annotations.%s", context.getAnnotation().getAnnotationName()),
                String.format("package %s;", packageNameOnly)
        ));

//        try (final FileWriter writer = new FileWriter(packageInfoFileFile)) {
//            writer.write(String.format("@org.jspecify.annotations.%s%s", context.getAnnotation().getAnnotationName(), System.lineSeparator()));
//            writer.write(String.format("package %s;", packageNameOnly));
//        }

        context.getLog().info(String.format("package-info.java créé pour le package: %s dans %s", packageNameOnly, context.getGeneratedSourcesDirectory()));
    }

}
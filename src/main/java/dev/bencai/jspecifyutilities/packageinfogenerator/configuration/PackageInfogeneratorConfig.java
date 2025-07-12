package dev.bencai.jspecifyutilities.packageinfogenerator.configuration;

import lombok.Data;
import org.apache.maven.project.MavenProject;

@Data
public class PackageInfogeneratorConfig {

    private final MavenProject project;

}
package dev.bencai.jspecifyutilities.packageinfogenerator.plugin;

import dev.bencai.jspecifyutilities.packageinfogenerator.configuration.JSpecifyAnnotationType;
import dev.bencai.jspecifyutilities.packageinfogenerator.core.Engine;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.MAIN_JAVA_DIRECTORY;
import static dev.bencai.jspecifyutilities.packageinfogenerator.core.Constants.MAIN_OUTPUT_DIRECTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/** Tests for {@link JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo}. */
@ExtendWith(MockitoExtension.class)
class JSpecifyPackageInfoMainJavaGeneratorGeneratorMojoTest {

    @Mock
    private MavenProject mockProject;
    @Mock
    private Log mockLog;
    @Mock
    private Build mockBuild;

    @Test
    void should_invoke_Engine_run_with_correct_context() throws Exception {
        // Arrange
        when(mockProject.getBasedir()).thenReturn(Paths.get("/project/base").toFile());
        when(mockProject.getBuild()).thenReturn(mockBuild);
        when(mockBuild.getDirectory()).thenReturn("/project/build");

        final JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo mojo = new JSpecifyPackageInfoMainJavaGeneratorGeneratorMojo();
        mojo.project = mockProject;
        mojo.skip = false;
        mojo.annotation = JSpecifyAnnotationType.NULL_MARKED;
        mojo.setLog(mockLog);

        final Path expectedSourceDirectory = Paths.get("/project/base", MAIN_JAVA_DIRECTORY);
        final Path expectedGeneratedSourcesDirectory = Paths.get("/project/build", MAIN_OUTPUT_DIRECTORY);

        try (final MockedStatic<Engine> mockedEngine = Mockito.mockStatic(Engine.class)) {
            // Act
            mojo.execute();

            // Assert
            mockedEngine.verify(() -> Engine.run(argThat(context -> {
                assertThat(context).isNotNull();
                assertThat(context.getProject()).isEqualTo(mockProject);
                assertThat(context.getLog()).isEqualTo(mockLog);
                assertThat(context.isSkip()).isFalse();
                assertThat(context.getAnnotation()).isEqualTo(JSpecifyAnnotationType.NULL_MARKED);
                assertThat(context.getSourcesDirectory()).isEqualTo(expectedSourceDirectory);
                assertThat(context.getGeneratedSourcesDirectory()).isEqualTo(expectedGeneratedSourcesDirectory);
                return true;
            })), times(1));
        }
    }

}
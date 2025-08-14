package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

/** Tests for {@link FailWhenGeneratedSourcesDirectoryExistsStrategy}. */
@ExtendWith(MockitoExtension.class)
class FailWhenGeneratedSourcesDirectoryExistsStrategyTest {

    private final FailWhenGeneratedSourcesDirectoryExistsStrategy failWhenGeneratedSourcesDirectoryExistsStrategy = new FailWhenGeneratedSourcesDirectoryExistsStrategy();
    @Mock
    private Log mockLog;
    @TempDir
    private Path tmpGeneratedSourcesDirectory;

    @Test
    void should_throw_exception() {
        Assertions.assertThatThrownBy(() -> failWhenGeneratedSourcesDirectoryExistsStrategy.whenExists(tmpGeneratedSourcesDirectory, mockLog))
                  .isInstanceOf(MojoExecutionException.class)
                  .hasMessage("Failed because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=FAIL'");
    }

}
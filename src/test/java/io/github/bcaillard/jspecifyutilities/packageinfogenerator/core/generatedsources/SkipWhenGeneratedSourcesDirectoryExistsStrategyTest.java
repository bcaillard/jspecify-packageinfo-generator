package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.SkipProcessException;
import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

/** Test for {@link SkipWhenGeneratedSourcesDirectoryExistsStrategy}. */
@ExtendWith(MockitoExtension.class)
class SkipWhenGeneratedSourcesDirectoryExistsStrategyTest {

    private final SkipWhenGeneratedSourcesDirectoryExistsStrategy skipWhenGeneratedSourcesDirectoryExistsStrategy = new SkipWhenGeneratedSourcesDirectoryExistsStrategy();
    @Mock
    private Log mockLog;
    @TempDir
    private Path tmpGeneratedSourcesDirectory;

    @Test
    void should_throw_SkipProcessException() {
        Assertions.assertThatThrownBy(() -> skipWhenGeneratedSourcesDirectoryExistsStrategy.whenExists(tmpGeneratedSourcesDirectory, mockLog))
                  .isInstanceOf(SkipProcessException.class)
                  .hasMessage("Skipping package-info.java generation because the generated sources directory already exists and 'whenGeneratedSourcesDirectoryExists=SKIP'");
    }

}
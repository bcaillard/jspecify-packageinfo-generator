package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.mockito.Mockito.verify;

/** Test for {@link RunWhenGeneratedSourcesDirectoryExistsStrategy}. */
@ExtendWith(MockitoExtension.class)
class RunWhenGeneratedSourcesDirectoryExistsStrategyTest {

    private final RunWhenGeneratedSourcesDirectoryExistsStrategy runWhenGeneratedSourcesDirectoryExistsStrategy = new RunWhenGeneratedSourcesDirectoryExistsStrategy();
    @Mock
    private Log mockLog;
    @TempDir
    private Path tmpGeneratedSourcesDirectory;

    @Test
    void should_only_log_and_does_nothing_else() {
        runWhenGeneratedSourcesDirectoryExistsStrategy.whenExists(tmpGeneratedSourcesDirectory, mockLog);

        verify(mockLog).debug("The process continues despite the existence of the generated sources directory, because 'whenGeneratedSourcesDirectoryExists=RUN'");
    }

}
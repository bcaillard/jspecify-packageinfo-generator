package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.generatedsources.GeneratedSourcesDirectoryExistsStrategy.SKIP;
import static org.mockito.Mockito.verify;

/** Tests for {@link GeneratedSourcesDirectoryExistsHandler}. */
@ExtendWith(MockitoExtension.class)
class GeneratedSourcesDirectoryExistsHandlerTest {

    @Mock
    private SkipWhenGeneratedSourcesDirectoryExistsStrategy mockSkipWhenGeneratedSourcesDirectoryExistsStrategy;
    @Mock
    private Log mockLog;
    @TempDir
    private Path tmpGeneratedSourcesDirectory;

    @Test
    void should_delegate_handling_to_selected_strategy() throws MojoExecutionException {
        GeneratedSourcesDirectoryExistsHandler.strategies.put(SKIP, mockSkipWhenGeneratedSourcesDirectoryExistsStrategy);

        GeneratedSourcesDirectoryExistsHandler.handle(SKIP, tmpGeneratedSourcesDirectory, mockLog);

        verify(mockSkipWhenGeneratedSourcesDirectoryExistsStrategy).whenExists(tmpGeneratedSourcesDirectory, mockLog);
    }

}
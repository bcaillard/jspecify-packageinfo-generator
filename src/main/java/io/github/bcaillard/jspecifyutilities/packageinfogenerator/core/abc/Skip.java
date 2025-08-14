package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.abc;

import java.nio.file.Path;

public class Skip implements GeneratedSourceDirectoryWhenPresentStrategy {

    @Override
    public void whenExists(final Path generated) {
        // throw skip
    }

}
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.abc;

import java.nio.file.Path;

public interface GeneratedSourceDirectoryWhenPresentStrategy {

    void whenExists(Path generated);

}
package io.github.bcaillard.jspecifyutilities.packageinfogenerator.core.abc;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.util.EnumMap;

@UtilityClass
public class GeneratedHandler {

    private static final EnumMap<Strategy, GeneratedSourceDirectoryWhenPresentStrategy> map = new EnumMap<>(Strategy.class);

    static {
        map.put(Strategy.SKIP, new Skip());
        map.put(Strategy.FAIL, new Fail());
        map.put(Strategy.RUN, new Run());
    }

    public static void handler(final Strategy strategy, final Path generated) {
        final GeneratedSourceDirectoryWhenPresentStrategy orDefault = map.get(strategy);
        orDefault.whenExists(generated);
    }

}
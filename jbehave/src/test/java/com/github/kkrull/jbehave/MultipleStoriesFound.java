package com.github.kkrull.jbehave;

import java.util.List;

public class MultipleStoriesFound extends RuntimeException {
    public static MultipleStoriesFound forGlobPattern(String glob, List<String> matchingPaths) {
        String message = String.format("Multiple stories found matching pattern '%s': %s", glob, matchingPaths);
        return new MultipleStoriesFound(message);
    }

    public MultipleStoriesFound(String message) {
        super(message);
    }
}

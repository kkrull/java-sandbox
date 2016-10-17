package com.github.kkrull.jbehave;

public class StoryNotFound extends RuntimeException {
    public static StoryNotFound forGlobPattern(String glob) {
        String message = String.format("No stories found matching pattern: %s", glob);
        return new StoryNotFound(message);
    }

    public StoryNotFound(String message) {
        super(message);
    }
}

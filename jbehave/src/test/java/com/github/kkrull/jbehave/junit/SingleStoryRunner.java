package com.github.kkrull.jbehave.junit;

import com.github.kkrull.jbehave.MultipleStoriesFound;
import com.github.kkrull.jbehave.StoryNotFound;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.STATS;

public abstract class SingleStoryRunner extends JUnitStory {
    @Override
    public final InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), stepDefinitionClass());
    }

    @Override
    public final Configuration configuration() {
        return new MostUsefulConfiguration()
            .useStoryPathResolver(singleStoryFileResolver(storyFilename()))
            .useStoryReporterBuilder(storyReporter());
    }

    private StoryPathResolver singleStoryFileResolver(String storyFilename) {
        return runnerClass -> pathMatchingGlob(String.format("**/%s", storyFilename));
    }

    private String pathMatchingGlob(String glob) {
        StoryFinder finder = new StoryFinder();
        List<String> paths = finder.findPaths(CodeLocations.codeLocationFromClass(getClass()), glob, "");
        switch(paths.size()) {
            case 0: throw StoryNotFound.forGlobPattern(glob);
            case 1: return paths.get(0);
            default: throw MultipleStoriesFound.forGlobPattern(glob, paths);
        }
    }

    private StoryReporterBuilder storyReporter() {
        return new StoryReporterBuilder().withFormats(ANSI_CONSOLE, STATS);
    }

    protected abstract String storyFilename();
    protected abstract Object stepDefinitionClass();
}

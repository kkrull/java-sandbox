package com.github.kkrull.jbehave;

import com.github.kkrull.jbehave.steps.StepDefinitionScanner;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;

public class GlobalJBehaveRunner extends JUnitStories {
    @Override
    protected List<String> storyPaths() {
        StoryFinder finder = new StoryFinder();
        return finder.findPaths(CodeLocations.codeLocationFromClass(getClass()), "**/*.story", "");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        Stream<?> stepDefinitionInstances = new StepDefinitionScanner().newInstances("com.github.kkrull");
        return new InstanceStepsFactory(configuration(), stepDefinitionInstances.collect(toList()));
    }

    @Override
    public Configuration configuration() {
        StoryReporterBuilder storyReporter = new StoryReporterBuilder().withFormats(ANSI_CONSOLE, HTML);
        return new MostUsefulConfiguration()
            .useStoryLoader(new LoadFromClasspath(getClass()))
            .useStoryReporterBuilder(storyReporter)
            .useStepMonitor(new SilentStepMonitor());
    }
}

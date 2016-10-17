package com.github.kkrull.fixture;

import com.github.kkrull.jbehave.junit.SingleStoryRunner;

public class FixtureRunner extends SingleStoryRunner {
    protected String storyFilename() { return "fixture.story"; }
    protected Object stepDefinitionClass() { return new FixtureSteps(); }
}

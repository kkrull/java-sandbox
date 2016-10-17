package com.github.kkrull.atm;

import com.github.kkrull.jbehave.junit.SingleStoryRunner;

public class ATMRunner extends SingleStoryRunner {
    protected String storyFilename() { return "atm.story"; }
    protected Object stepDefinitionClass() { return new ATMSteps(); }
}

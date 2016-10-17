package com.github.kkrull.oracle;

import com.github.kkrull.jbehave.junit.SingleStoryRunner;

public class OracleRunner extends SingleStoryRunner {
    protected String storyFilename() { return "oracle.story"; }
    protected Object stepDefinitionClass() { return new OracleSteps(); }
}

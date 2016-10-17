package com.github.kkrull.fixture;

import com.github.kkrull.jbehave.steps.Steps;
import org.jbehave.core.annotations.*;

@Steps
public class FixtureSteps {
    @BeforeScenario
    public void scenarioFixture_getsCalledWhetherTheTagIsThereOrNot_thatIsSoStupid(@Named("scenarioFixture") String onOrBlank) {
        switch(onOrBlank.toLowerCase()) {
            case "on": TestFixture.setup(); return;
            default: return;
        }
    }

    @Given("I have a before-scenario method tagged to set up a test fixture")
    public void givenIHaveABeforestoryMethodTaggedToSetUpATestFixture() { /* do nothing */ }

    @Given("I tag this scenario to use that fixture")
    public void givenITagThisScenarioToUseThatFixture() { /* do nothing */ }

    @Given("I do not tag this scenario to use that fixture")
    public void givenIDoNotTagThisScenarioToUseThatFixture() { /* do nothing */ }

    @When("I run this scenario")
    public void whenIRunThisScenario() { /* do nothing */ }

    @Then("that method has been executed")
    public void thenThatMethodHasBeenExecuted() {
        TestFixture.shouldHaveBeenSetUpOnce();
    }

    @Then("that method has not been executed for this scenario")
    public void thenThatMethodHasNotBeenExecutedForThisScenario() {
        TestFixture.shouldNotHaveBeenSetUpAgain();
    }
}

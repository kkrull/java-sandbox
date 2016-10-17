package com.github.kkrull.oracle;

import com.github.kkrull.jbehave.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Steps
public class OracleSteps {

    private Oracle subject;
    private String answer;

    @Given("an oracle")
    public void givenAnOracle() {
        this.subject = new Oracle();
    }

    @When("I ask it for the answer")
    public void whenIAskItForTheAnswer() {
        this.answer = subject.getAnswerToTheUniverse();
    }

    @Then("it responds with the universal answer")
    public void thenItRespondsWithTheUniversalAnswer() {
        assertThat(answer, equalTo("Forty two"));
    }
}

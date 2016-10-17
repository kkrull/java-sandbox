Narrative: Use a test fixture
As a developer
I want a test fixture to run before and after my scenario
In order to start and stop services I need during the test


Scenario: The fixture should run when the scenario is tagged appropriately
Meta:
@scenarioFixture on
@somethingElse off

Given I have a before-scenario method tagged to set up a test fixture
And I tag this scenario to use that fixture
When I run this scenario
Then that method has been executed

Scenario: The fixture should not run when the scenario is not tagged for it
Given I have a before-scenario method tagged to set up a test fixture
Given I do not tag this scenario to use that fixture
When I run this scenario
Then that method has not been executed for this scenario

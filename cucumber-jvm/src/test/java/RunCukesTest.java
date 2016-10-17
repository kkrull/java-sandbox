import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import org.junit.runner.RunWith;

/**
 * The one and only test runner for Cucumber tests.
 *
 * The name of the class ends in 'Test' so that maven-surefire-plugin runs it during `mvn test`.
 *
 * When this test runs, this:
 * - scans the classpath for `.feature` files
 * - scans for classes in the classpath that contain methods tagged with @Given, @When and @Then
 * - runs one test per scenario (or per example, if using a Scenario Outline), executing the methods whose regular
 *   expression matches the text in the scenario
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin="pretty", snippets=SnippetType.CAMELCASE)
public class RunCukesTest { /* empty */ }

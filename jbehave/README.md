# An Example  [JBehave](http://jbehave.org/) Project using Maven

Forked from [some other guy](https://github.com/masterthought/jbehave-example).  Or gal.  I don't really know.

Thanks, other guy.  


## Running Tests

Run JUnit tests like always: `mvn test`, or the convenience script `bin/junit`.

Run JBehave tests with `mvn verify`, or the convenience script `bin/jbehave`.   

Note that this will also run JUnit tests.  If you want to run JBehaves and only JBehaves, you'd have to do something
more compilcated like set up a profile that excludes `**/Test.java` from Surefire's configuration when running JBehaves.  

See the documentation for the [failsafe plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/index.html) if
you're curious about why it's best to say `mvn verify` instead of `mvn integration-test` (the former ensures proper
setup and teardown of any test fixtures needed for running integration tests).


## Rant on `jbehave-maven-plugin`

In a cruel and ironic twist of fate, getting `jbehave-maven-plugin` to actually run 2 or more JBehave scenarios
successfully is exceedingly difficult.  Yes that's right: the plugin written for the express purpose of running JBehave
scenarios is hard to use for that, very purpose.

Its list of transgressions is a long one:

- It forces the use of opaque, poorly-documented naming conventions that relate Java Scenario (glue code) classes to
  `.story` files
- It violates the Maven standard directory layout (`src/main` instead of `src/test` by default), and we all know what
  happens in Maven when you try to use a non-standard directory layout.
- It uses flaky path name glob patterns to glue code *source* instead of compiled code.  This seems like it's asking for
  a brittle project that runs tests in a development environment, but nowhere else (i.e. from a jar file).
- Last - but not least - **it doesn't run the tests!!** (if you have two or more `Scenario` files).  **!!**

I was really hoping to avoid this, but I've had to follow the oft-recommended approach of using the
`maven-failsafe-plugin` to run JBehave scenarios.  Although it seems to be working, I don't like how indirect it is:

- Maven invokes the failsafe plugin, which...
- calls the surefire plugin, which...
- (presumably) invokes some sort of JUnit runner, which...
- (presumably) scans the classpath for `@Test` methods, which...
- finds classes descending from `JUnitStory` and `JUnitStories`, which...
- instantiate step definition classes and associate them with related `.story` files.

In short, there are many layers at which things can get mis-configured and go horribly, horribly wrong.

*It's a miracle it works at all.*

Your reward - by the way - for all this hard work is **incredibly noisy test output** that looks impressive as hell when
things go right, but God help you if you have to sort through all that cruft to find what happened during a failing
test.  Arg!!


## Maven Configuration and Test Discovery

So as I mentioned it's the `maven-failsafe-plugin` that needs to be configured to look for tests (JUnit tests, in this
case).  This is done by a 1-time configuration in `build/../plugin/configuration/includes` in `pom.xml`.  It looks for
the 1 glue-code class that:

- Discovers all JBehave `.story` files
- Discovers and instantiates all Step Definition classes annotated with the included `@Steps`.
  **Note that the package name will need to be changed to suit your needs.**
- Instantiates the Step Definition classes using a public, no-arg constructor.

The bright side after this one-time configuration is that you don't have to mess with it afterwards.  Add new `.story` files
to `src/test/resources/<your base package>` and new `@Steps`-annotated classes to `src/test/java/<your base package>`
for your Step Definitions.  Also on the bright side - discovering `.story` files and having a single glue code class
seems to avoid the dreaded `FileNotFoundException: Missing file storyDurations.props` error that is so difficult to
debug.

There is, by the way, a bit of extra weirdness required for JBehave to not barf when generating reports.  That's the bit
about the `unpack-view-resources` execution and corresponding `zip`-type dependencies because...SOMEBODY...does not
declare those files needed for the view as runtime dependencies or include them in the other JBehave libraries you
already have to download.


## Console Output

- `StoryReporterBuilder.withFormats(CONSOLE)`: `(BeforeStories)`, story filenames, contents of story files.
- `StoryReporterBuilder.withFormats(ANSI_CONSOLE)`: The same as `CONSOLE`, but with color output.


## Test fixture

JBehave has a feature called [Meta Filtering](http://jbehave.org/reference/stable/meta-filtering.html), which at first
looks similar to cucumber-jvm tags.  If they were so similar, you could use a method like this to run any fixture
setup:

```java
@BeforeStory
public void setup(@Named("storyFixture") String noValueNecessary) {
    new Fixture().setup();
}

```

Alas, it does not work as I expected:

- This method gets called whether or not you have the named meta tag.
- This method gets called if you have the named meta tag, and comment it out.  It still gets called with whatever
  value is associated with the tag (i.e. commenting it out has absolutely no effect).

As such, any fixture method has to check to see if there's a value.  So the notion of doing meta filtering based upon
the mere existence of a tag is a myth.  `Meta: @RunWithMyCoolFixture` is a construct that invokes dismay and rage the
instant you add a second scenario or story.

# Gradle 6 Sandbox

## Prerequisites

* Gradle 6.7.1: https://docs.gradle.org/current/samples/sample_building_java_applications.html
* OpenJDK 11.0.9.1+1

`gradle init`: Pick application, Java, Groovy, JUnit Jupiter.

## VS Code

Set in `settings.json`

"java.home": "C:\\Program Files\\AdoptOpenJDK\\jdk-11.0.9.101-hotspot"

Note the excaped backslashes


## Usage

```
./gradlew tasks #list tasks
./gradlew build #compile and test all projects
./gradlew run #run the application
./gradlew :app:dependencies #list dependencies
```

## Testing

```
./gradlew test #run the tests (may skip some if sources have not changed)
./gradlew cleanTest test #force tests to run again
```

See [Forcing tests to
run](https://docs.gradle.org/current/userguide/java_testing.html#sec:forcing_java_tests_to_run)
for details.

Test reports are generated in `app/build/repots/tests/test/index.html`.  Open
this in a browser to see what's going on.

Or you can use the console runner in the junit5 standalone jar.  Download and
run it with `scripts/test.sh`.

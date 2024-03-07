# Java 21 Hello World

Trying out Java 21 for the first time.

## Creation

```sh
gradle init
# Java application with multiple projects
# Use Groovy for Gradle configuration
```

## Tasks

### `./gradlew clean`

Remove compiled code and packages.

### `./gradlew run`

Run the application.

### `./gradlew test`

Run tests.

## Tools

- Gradle 8.6 (homebrew: `gradle`)
- `jenv` 0.5.6 (homebrew: `jenv`)
  - Then enable the `global` plugin so that `JAVA_HOME` gets exported: `jenv enable-plugin export`.
    This is required for tools such as Gradle and VS Code extensions for Gradle to work correctly.
- openjdk 21.0.2 2024-01-16 (homebrew: `openjdk`)
- VS Code extensions
  - Extension Pack for Java (`vscjava.vscode-java-pack`)
  - Gradle for Java (`vscjava.vscode-gradle`)
    - Note: `JAVA_HOME` needs to be set, for this extension to work.  Try running VS Code as
      `JAVA_HOME=/usr/local/Cellar/openjdk/21.0.2 code .`  See:
      <https://stackoverflow.com/questions/76744155/no-connection-to-gradle-server-the-gradle-client-was-unable-to-connect-in-vis>
  - Test Runner for Java (`vscjava.vscode-java-test`)

### jenv, Gradle, VSCode Compatibility

`jEnv` doesn't want to set JAVA_HOME for some reason: <https://github.com/jenv/jenv/issues/44>

But the Gradle for Java extension for VSCode needs it to be set, in order for the extension to use
the right version of Java for running the build.  One workaround is to start VSCode with its own environment variables, so that `jEnv` is still happy:

```sh
jenv exec code .
```

Or better yet, do this one-time configuration for jenv:

```sh
jenv enable-plugin export
```

as mentioned here: <https://github.com/jenv/jenv/issues/44#issuecomment-233124185>

Then jenv acknowledges that JAVA_HOME is set, by itself, and it is all green.  And VSCode seems to
work as just `code .` once again.

It's described a little better here: <https://github.com/jenv/jenv#11-installing-jenv>

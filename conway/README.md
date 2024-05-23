# Coderetreat Base Project: Java

Minimal setup with Java that uses
[JUnit5](https://junit.org/junit5/docs/current/user-guide/) for testing and
[gradle](https://gradle.org/) for build automation.


## Requirements

This project was developed on

* [Gradle 6](https://docs.gradle.org/6.8.1/userguide/userguide.html)
* [OpenJDK 11](https://jdk.java.net/java-se-ri/11)


### MacOS installation with Homebrew

```shell
# Set up homebrew taps
$ brew tap homebrew/cask
$ brew tap homebrew/cask-versions

# Install packages
$ brew install openjdk@11
$ brew install gradle@6
```

Installation instructions are derived from here:
https://gist.github.com/gwpantazes/50810d5635fc2e053ad117b39b597a14#openjdk-11


### Windows

1. Download [OpenJDK 11 for Windows](https://jdk.java.net/java-se-ri/11) and
   extract it to some place like `C:\jdk-11.0.1`.
1. Set the following environment variables (windows key -> "Edit system
   environment variables"):
   * Create a new varirable `JAVA_HOME` and set it to the directory where you
     extracted the JDK (like `C:\jdk-11.0.1`).
   * Edit `PATH` to include this entry `%JAVA_HOME%\bin`.
     * Windows 7: Prepend the value to the existing string, as in
       `%JAVA_HOME%\bin;<whatever was there before...>`
     * Windows 10: Add an entry for `%JAVA_HOME%\bin` and press "Move Up" until
       it's at the top.
1. Restart any command prompts that are open, so that they have the environment
   settings you just created.
1. Open a command prompt and make sure `java -version` prints out something
   about it being OpenJDK 11.


Reference:
https://knowledge.exlibrisgroup.com/Aleph/Knowledge_Articles/How_to_Download_and_Install_OpenJDK_11_on_Windows_10_PC_for_Aleph


## Running Tests

Run tests with `./gradlew test` (`gradlew.bat test` on Windows), or run them in
your favorite IDE.


## Troubleshooting

Sometimes there are multiple JDK versions installed on your machine, and these
can help:

1. Which JDKs are installed: `/usr/libexec/java_home`
2. Set a version when running a command: `JAVA_VERSION=11 ./gradlew test`
3. Set a version for good: `export JAVA_VERSION=11`

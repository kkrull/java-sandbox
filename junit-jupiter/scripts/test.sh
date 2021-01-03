#!/usr/bin/env bash

set -e

console_runner_jar='junit-platform-console-standalone-1.7.0.jar'
if [ ! -f "$console_runner_jar" ]; then
  echo "Downloading console runner"
  curl 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.0/junit-platform-console-standalone-1.7.0.jar' \
    --output "$console_runner_jar" \
    --silent
fi

#Get the jar: https://mvnrepository.com/artifact/org.junit.platform/junit-platform-console-standalone/1.7.0
cd app
exec java -jar "../$console_runner_jar" \
  --class-path build/classes/java/test \
  --class-path build/classes/java/main \
  --scan-class-path \
  -e junit-jupiter

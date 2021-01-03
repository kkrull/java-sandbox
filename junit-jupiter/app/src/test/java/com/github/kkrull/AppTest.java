package com.github.kkrull;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("Console Application")
class AppTest {
  private App subject;

  AppTest() {
    this.subject = new App();
  }

  @Nested
  @DisplayName("#getGreeting")
  class getGreeting {
    @DisplayName("greets the world, given no name")
    @Test void givenNoName() {
      assertEquals("Hello World!", subject.getGreeting());
    }

    @DisplayName("greets the person by name, given a name")
    @Test void givenAName() {
      assertEquals("Hello George!", subject.getGreeting("George"));
    }
  }

  //TODO KDK: Try generating some tests next  https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
}

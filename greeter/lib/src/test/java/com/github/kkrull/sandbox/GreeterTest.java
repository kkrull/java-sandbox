package com.github.kkrull.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("Greeter")
class GreeterTest {
  @DisplayName("#makeGreeting")
  @Nested
  class makeGreeting {
    @DisplayName("addresses the whole world")
    @Test
    void addressesTheWorld() {
      Greeter subject = new Greeter();
      assertEquals("Hello World!", subject.makeGreeting());
    }
  }
}

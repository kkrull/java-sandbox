package com.github.kkrull.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GreeterTest {
  @Test
  void makeGreetingAddressesTheWholeWorld() {
    Greeter subject = new Greeter();
    assertEquals("Hello World!", subject.makeGreeting());
  }
}

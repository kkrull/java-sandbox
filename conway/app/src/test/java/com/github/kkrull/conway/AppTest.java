package com.github.kkrull.conway;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
  @Test
  void appHasAGreeting() {
    App subject = new App();
    assertEquals("Hello World!", subject.getGreeting());
  }

  @Test
  @Disabled("TODO Write your next test here")
  void nextTest() {
    //Something it not quite right here...
    int universalAnswer = 41;
    assertEquals(42, universalAnswer);
  }
}

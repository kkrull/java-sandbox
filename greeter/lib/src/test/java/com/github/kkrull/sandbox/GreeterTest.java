package com.github.kkrull.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreeterTest {
  @Test
  void testSomeLibraryMethod() {
    Greeter classUnderTest = new Greeter();
    assertTrue(classUnderTest.someLibraryMethod(), "someLibraryMethod should return 'true'");
  }
}

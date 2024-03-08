package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RomanNumeralTest {
  @Test
  void givenOne() throws Exception {
    String romanNumeral = RomanNumeral.convert(1);
    assertEquals("I", romanNumeral);
  }

  @Test
  void givenTwo() throws Exception {
    String romanNumeral = RomanNumeral.convert(2);
    assertEquals("II", romanNumeral);
  }

  @Test
  void givenThree() throws Exception {
    String romanNumeral = RomanNumeral.convert(3);
    assertEquals("III", romanNumeral);
  }
}

package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RomanNumeralTest {
  @Test
  void eachICountsAsOne() throws Exception {
    assertEquals("I", RomanNumeral.convert(1));
    assertEquals("II", RomanNumeral.convert(2));
    assertEquals("III", RomanNumeral.convert(3));
  }
}

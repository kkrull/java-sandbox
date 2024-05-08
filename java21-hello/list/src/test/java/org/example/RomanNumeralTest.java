package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RomanNumeralTest {
  @Test
  void eachLetterICountsAsOne() throws Exception {
    assertEquals("I", RomanNumeral.convert(1));
    assertEquals("II", RomanNumeral.convert(2));
    assertEquals("III", RomanNumeral.convert(3));
  }

  @Test
  void letterVCountsAsFive() throws Exception {
    assertEquals("V", RomanNumeral.convert(5));
  }
}

package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    if(number == 1) {
      return "I";
    }

    if (number == 2) {
      return "I" + "I";
    }

    return "I" + "I" + "I";
  }
}

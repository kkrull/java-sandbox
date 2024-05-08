package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    if (number == 5) {
      return "V";
    }

    String romanNumeral = "";
    for (int i = 0; i < number; i = i + 1) {
      romanNumeral = romanNumeral + "I";
    }

    return romanNumeral;
  }
}

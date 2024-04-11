package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    if(number == 1) {
      String romanNumeral = "";
      romanNumeral = romanNumeral + "I";
      return romanNumeral;
    }

    if (number == 2) {
      String romanNumeral = "";
      romanNumeral = romanNumeral + "I";
      romanNumeral = romanNumeral + "I";
      return romanNumeral;
    }

    String romanNumeral = "";
    for(int i = 0; i < 3; i = i + 1) {
      romanNumeral = romanNumeral + "I";
    }

    return romanNumeral;
  }
}
